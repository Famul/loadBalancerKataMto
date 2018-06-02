package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static edu.iis.mto.serverloadbalancer.serverLoadPercentageMatcher.hasCurrentLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.serverVmsCountMatcher.hasCountOfVms;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void serverWithNoVmShouldBeEmpty() {
        Server theServer = a(server().withCapacityOf(1));

        balancing(aListOfServersWith(theServer), anEmptyListOfVms());

        assertThat(theServer, hasCurrentLoadPercentageOf(0.0d));
    }

    @Test
    public void serverWithCapacityEqualToSizeOfVmShouldBeFull() {
        Server theServer = a(server().withCapacityOf(1));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasCurrentLoadPercentageOf(100.0d));
        assertThat("the server contains the vm", theServer.contains(theVm));

    }

    @Test
    public void serverWithCapacityHigherThanVmSizeShouldBePartiallyFilled() {
        Server theServer = a(server().withCapacityOf(10));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasCurrentLoadPercentageOf(10.0d));
        assertThat("the server contains the vm", theServer.contains(theVm));
    }

    @Test
    public void serverWithEnoughRoomShouldContainMultipleVms() {
        Server theServer = a(server().withCapacityOf(10));
        Vm theFirstVm = a(vm().ofSize(3));
        Vm theSecondVm = a(vm().ofSize(4));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theFirstVm, theSecondVm));

        assertThat(theServer, hasCountOfVms(2));
        assertThat("the server contains the first vm", theServer.contains(theFirstVm));
        assertThat("the server contains the second vm", theServer.contains(theSecondVm));
    }

    @Test
    public void vmShouldBePlacedOnLessLoadedServer() {
        Server theMoreLoadedServer = a(server().withCapacityOf(10).withCurrentLoadOf(50.0d));
        Server theLessLoadedServer = a(server().withCapacityOf(10).withCurrentLoadOf(45.0d));

        Vm theVm = a(vm().ofSize(3));

        balancing(aListOfServersWith(theMoreLoadedServer, theLessLoadedServer), aListOfVmsWith(theVm));

        assertThat("the less loaded server contains the vm", theLessLoadedServer.contains(theVm));
        assertThat("the more loaded server not contains the vm", !theMoreLoadedServer.contains(theVm));
    }

    private Vm[] aListOfVmsWith(Vm... vms) {
        return vms;
    }

    private void balancing(Server[] servers, Vm[] vms) {
        new ServerLoadBalancer().balance(servers, vms);
    }

    private Vm[] anEmptyListOfVms() {
        return new Vm[0];
    }

    private <T> T a(Builder<T> builder) {
        return builder.build();
    }

    private Server[] aListOfServersWith(Server... servers) {
        return servers;
    }

}
