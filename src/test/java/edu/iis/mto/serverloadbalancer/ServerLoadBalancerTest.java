package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;
import org.junit.Test;

public class ServerLoadBalancerTest {

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void serverWithNoVmsShouldBeEmpty() {
        Server theServer = a(server().withCapacity(1));

        balancing(aServersListWith(theServer), anEmptyListOfVMs());

        Assert.assertThat(theServer, hasCurrentLoadOf(0.0d));
    }

    @Test
    public void serverWithOneSlotFilledWithOneVmShouldBeFull() {
        Server theServer = a(server().withCapacity(1));
        Vm theVm = a(vm().ofSize(1));
        balancing(aServersListWith(theServer), aVmsListWith(theVm));

        assertThat(theServer, hasCurrentLoadOf(100.0d));
        assertThat("server should contain the vm", theServer.contains(theVm));
    }

    private Vm[] aVmsListWith(Vm... vms) {
        return vms;
    }

    private Vm a(VmBuilder vmBuilder) {
        return vmBuilder.build();
    }

    private VmBuilder vm() {
        return new VmBuilder();
    }

    private void balancing(Server[] servers, Vm[] vms) {
        new ServerLoadBalancer().balance(servers, vms);
    }

    private Vm[] anEmptyListOfVMs() {
        return new Vm[0];
    }

    private Server[] aServersListWith(Server... servers) {
        return servers;
    }

    private Server a(ServerBuilder serverBuilder) {
        return serverBuilder.build();
    }

}
