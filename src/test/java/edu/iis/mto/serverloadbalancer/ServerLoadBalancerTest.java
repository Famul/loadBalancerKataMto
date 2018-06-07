package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerLoadPercentageMatcher.hasCurrentLoadPercentageOF;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void serverWithNoVmsShouldStayEmpty() {
        Server theServer = a(server().withCapacityOf(1));

        balancing(aListOFServersWith(theServer), anEmptyListOfVms());

        assertThat(theServer, hasCurrentLoadPercentageOF(0.0d));
    }

    @Test
    public void serverWithVmOfSizeEqualToServerCapacityShouldBeFull() {
        Server theServer = a(server().withCapacityOf(1));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOFServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasCurrentLoadPercentageOF(100.0d));
        assertThat("the server should contain the vm", theServer.contains(theVm));
    }

    private Vm a(VmBuilder builder) {
        return builder.build();
    }

    private Vm[] aListOfVmsWith(Vm... vms) {
        return vms;
    }

    private VmBuilder vm() {
        return new VmBuilder();
    }

    private void balancing(Server[] servers, Vm[] vms) {
        new ServerLoadBalancer().balance(servers, vms);
    }

    private Vm[] anEmptyListOfVms() {
        return new Vm[0];
    }

    private Server a(ServerBuilder builder) {
        return builder.build();
    }

    private Server[] aListOFServersWith(Server... servers) {
        return servers;
    }

}
