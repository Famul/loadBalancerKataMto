package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static edu.iis.mto.serverloadbalancer.serverLoadPercentageMatcher.serverWithCurrentLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.serverVmsCountMatcher.hasVmsCountOf;
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

    @Test
    public void serverWithVmSmallerThanServerCapacityShouldBePartiallyLoaded() {
        Server theServer = a(server().withCapacityOf(10));
        Vm theVm = a(vm().ofSize(1));
        balancing(aServerListWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, serverWithCurrentLoadPercentageOf(10.0d));
        assertThat("the server should contain the vm", theServer.contains(theVm));

    }

    @Test
    public void serverWithEnoughRoomShoulStoreMultipleVms() {
        Server theServer = a(server().withCapacityOf(10));
        Vm theFirstVm = a(vm().ofSize(1));
        Vm theSecondVm = a(vm().ofSize(1));
        balancing(aServerListWith(theServer), aListOfVmsWith(theFirstVm, theSecondVm));

        assertThat(theServer, hasVmsCountOf(2));
        assertThat("the server should contain the first vm", theServer.contains(theFirstVm));
        assertThat("the server should contain the second vm", theServer.contains(theSecondVm));

    }

    @Test
    public void vmShouldBePlacedOnLessLoadedServer() {
        Server theMoreLoadedServer = a(server().withCapacityOf(10).withCurrentLoadPercentage(50.0d));
        Server theLessLoadedServer = a(server().withCapacityOf(10).withCurrentLoadPercentage(45.0d));
        Vm theVm = a(vm().ofSize(1));
        balancing(aServerListWith(theMoreLoadedServer, theLessLoadedServer), aListOfVmsWith(theVm));

        assertThat("the more loaded server should not contain the first vm", !theMoreLoadedServer.contains(theVm));
        assertThat("the less loaded server should contain the second vm", theLessLoadedServer.contains(theVm));

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

    private Server[] aServerListWith(Server... servers) {
        return servers;
    }

}
