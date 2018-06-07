package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerLoadPercentageMatcher.hasCurrentLoadPercentageOF;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
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

    @Test
    public void serverWithVmOfSizeSmallerThanServerCapacityShouldFillServerPartially() {
        Server theServer = a(server().withCapacityOf(10));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOFServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasCurrentLoadPercentageOF(10.0d));
        assertThat("the server should contain the vm", theServer.contains(theVm));
    }

    @Test
    public void serverWithEnoughRoomShouldBeLoadedWithMultipleVms() {
        Server theServer = a(server().withCapacityOf(10));
        Vm theFirstVm = a(vm().ofSize(2));
        Vm theSecondVm = a(vm().ofSize(2));

        balancing(aListOFServersWith(theServer), aListOfVmsWith(theFirstVm, theSecondVm));

        assertThat(theServer, hasVmsCountOf(2));
        assertThat("the server should contain the vm", theServer.contains(theFirstVm));
        assertThat("the server should contain the vm", theServer.contains(theSecondVm));

    }

    @Test
    public void vmsShouldBePlacedOnLessLoadedServer() {
        Server theMoreLoadedServer = a(server().withCapacityOf(10).withCurrentLoadPercentageOf(50.0d));
        Server theLessLoadedServer = a(server().withCapacityOf(10).withCurrentLoadPercentageOf(45.0d));

        Vm theVm = a(vm().ofSize(2));

        balancing(aListOFServersWith(theMoreLoadedServer, theLessLoadedServer), aListOfVmsWith(theVm));

        assertThat("the less loaded server should contain the vm", theLessLoadedServer.contains(theVm));
        assertThat("the more loaded server should not contain the vm", !theMoreLoadedServer.contains(theVm));

    }

    @Test
    public void vmShouldNotBePlacedInServerWithNotEnoughRoom() {
        Server theServer = a(server().withCapacityOf(10).withCurrentLoadPercentageOf(90.0d));
        Vm theVm = a(vm().ofSize(3));

        balancing(aListOFServersWith(theServer), aListOfVmsWith(theVm));

        assertThat("the server should not contain the vm", !theServer.contains(theVm));
    }

    @Test
    public void balancingMultipleServersWithMultipleVms() {
        Server theFirstServer = a(server().withCapacityOf(4));
        Server theSecondServer = a(server().withCapacityOf(6));

        Vm theFirstVm = a(vm().ofSize(2));
        Vm theSecondVm = a(vm().ofSize(4));
        Vm theThirdVm = a(vm().ofSize(1));

        balancing(aListOFServersWith(theFirstServer, theSecondServer),
                aListOfVmsWith(theFirstVm, theSecondVm, theThirdVm));

        assertThat("the first server should contain the first vm", theFirstServer.contains(theFirstVm));
        assertThat("the second server should contain the second vm", theSecondServer.contains(theSecondVm));
        assertThat("the first server should contain the third vm", theFirstServer.contains(theThirdVm));

        assertThat(theFirstServer, hasCurrentLoadPercentageOF(75.0d));
        assertThat(theSecondServer, hasCurrentLoadPercentageOF(66.66d));
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

    private Server[] aListOFServersWith(Server... servers) {
        return servers;
    }

}
