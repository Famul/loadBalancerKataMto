package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasAVmCountOf;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static edu.iis.mto.serverloadbalancer.loadPercentageMatcher.hasALoadPercentageOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void serverWithOneSlotAndZeroVmsShouldStayEmpty() {
        Server theServer = a(server().withCapacity(1));

        balancing(aListOfServersWith(theServer), anEmptyListOfVms());
        assertThat(theServer, hasALoadPercentageOf(0.0d));
    }

    @Test
    public void serverWithOneSlotCapacityAndOneVmOfSizeOneShouldBeFull() {
        Server theServer = a(server().withCapacity(1));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasALoadPercentageOf(100.0d));
        assertThat("the server should contain the vm", theServer.contains(theVm));

    }

    @Test
    public void serverWithTenSlotCapacityAndOneVmOfSizeOneShouldHaveLoadPercentageOfTenPercent() {
        Server theServer = a(server().withCapacity(10));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasALoadPercentageOf(10.0d));
        assertThat("the server should contain the vm", theServer.contains(theVm));

    }

    @Test
    public void serverWithEnoughRoomShouldContainAllVms() {
        Server theServer = a(server().withCapacity(10));
        Vm theFirstVm = a(vm().ofSize(3));
        Vm theSecondVm = a(vm().ofSize(4));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theFirstVm, theSecondVm));

        assertThat(theServer, hasAVmCountOf(2));
        assertThat("the server should contain the first vm", theServer.contains(theFirstVm));
        assertThat("the server should contain the second vm", theServer.contains(theSecondVm));

    }

    @Test
    public void vmsShouldBePlacedOnLessLoadedServer() {
        Server theLessLoadedServer = a(server().withCapacity(10).withInitialLoad(45.0d));
        Server theMoreLoadedServer = a(server().withCapacity(10).withInitialLoad(50.0d));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(theMoreLoadedServer, theLessLoadedServer), aListOfVmsWith(theVm));

        assertThat("the less loaded server should contain the vm", theLessLoadedServer.contains(theVm));
        assertThat("the more loaded server should not contain the vm", !theMoreLoadedServer.contains(theVm));

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
