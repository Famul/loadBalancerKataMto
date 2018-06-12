package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.serverLoadPercentageMatcher.serverWithCurrentLoadPercentageOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void serverWithNoVmsShouldBeEmpty() {
        Server theServer = a(server().withCapacityOf(1));

        balancing(aServerListWith(theServer), anEmptyListOfVms());

        assertThat(theServer, serverWithCurrentLoadPercentageOf(0.0d));
    }

    @Test
    public void serverWithVmEqualToCapacityShouldBeFull() {
        Server theServer = a(server().withCapacityOf(1));
        Vm theVm = a(vm().ofSize(1));
        balancing(aServerListWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, serverWithCurrentLoadPercentageOf(100.0d));
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

    private Server[] aServerListWith(Server... servers) {
        return servers;
    }

}
