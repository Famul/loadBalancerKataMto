package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerBaseTest {

    private int serverCapacity;
    private int vmSize;

    @Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {{2, 2}, {5, 5}, {8, 8}, {14, 14}, {25, 25}});
    }

    public ServerLoadBalancerParametrizedTest(int serverCapacity, int vmSize) {
        this.serverCapacity = serverCapacity;
        this.vmSize = vmSize;
    }

    @Test
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
        Server theServer = a(server().withCapacity(serverCapacity));
        Vm theVm = a(vm().ofSize(vmSize));
        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasLoadPercentageOf(100.0d));
        assertThat("the server should contain vm", theServer.contains(theVm));
    }

}
