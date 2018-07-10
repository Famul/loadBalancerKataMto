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
    private int numberOfServers;
    private int numberOfVms;
    private int[] capacities;
    private int[] sizes;
    private double[] loadPercentages;

    /*
     * @Parameters public static Collection<Object[]> testData() { return Arrays.asList(new Object[][] {{2, 2, 2, 3, {6,
     * 10}, {}, {}}, {5, 5}, {8, 8}, {14, 14}, {25, 25}}); }
     * 
     */

    @Parameters
    public static Collection<Object[][]> testData() {
        return Arrays.asList(new Object[][][] {{{2}, {2}, {2}, {3}, {6, 10}, {3, 1, 5}, {50.00, 60.00}},
                {{5}, {5}, {3}, {4}, {9, 4, 8}, {1, 2, 3, 5}, {66.66, 50.00, 37.50}},
                {{10}, {10}, {2}, {4}, {15, 24}, {3, 4, 2, 5}, {53.33, 25.00}},
                {{23}, {23}, {4}, {3}, {14, 4, 10, 2}, {7, 9, 1}, {50.00, 25.00, 90.00, 0.00}},
                {{1}, {1}, {3}, {4}, {10, 4, 6}, {1, 6, 4, 8}, {90.00, 100.00, 100.00}}});
    }

    public ServerLoadBalancerParametrizedTest(Object[] serverCapacity, Object[] vmSize, Object[] numberOfServers,
            Object[] numberOfVms, Object[] capacities, Object[] sizes, Object[] loadPercentages) {
        this.serverCapacity = ((Integer) serverCapacity[0]).intValue();
        this.vmSize = ((Integer) vmSize[0]).intValue();
        this.numberOfServers = ((Integer) numberOfServers[0]).intValue();
        this.numberOfVms = ((Integer) numberOfVms[0]).intValue();
        this.capacities = castObjectArrayToIntArray(capacities);
        this.sizes = castObjectArrayToIntArray(sizes);
        this.loadPercentages = castObjectArrayToDoubleArray(loadPercentages);
    }

    public int[] castObjectArrayToIntArray(Object[] object) {
        int[] array = new int[object.length];
        for (int i = 0; i < object.length; i++) {
            array[i] = ((Integer) object[i]).intValue();
        }
        return array;
    }

    public double[] castObjectArrayToDoubleArray(Object[] object) {
        double[] array = new double[object.length];
        for (int i = 0; i < object.length; i++) {
            array[i] = ((Double) object[i]).doubleValue();
        }
        return array;
    }

    @Test
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
        Server theServer = a(server().withCapacity(serverCapacity));
        Vm theVm = a(vm().ofSize(vmSize));
        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasLoadPercentageOf(100.0d));
        assertThat("the server should contain vm", theServer.contains(theVm));
    }

    @Test
    public void balancingMultipleServersWithMultipleVms() {
        Server[] listOfServers = new Server[numberOfServers];
        for (int i = 0; i < numberOfServers; i++) {
            listOfServers[i] = a(server().withCapacity(capacities[i]));
        }
        Vm[] listOfVms = new Vm[numberOfVms];
        for (int i = 0; i < numberOfVms; i++) {
            listOfVms[i] = a(vm().ofSize(sizes[i]));
        }

        balance(listOfServers, listOfVms);

        // assertThat("the first server should contain the first vm", theFirstServer.contains(theFirstVm));
        // assertThat("the second server should contain the second vm", theSecondServer.contains(theSecondVm));
        // assertThat("the first server should contain the third vm", theFirstServer.contains(theThirdVm));
        for (int i = 0; i < numberOfServers; i++) {
            assertThat(listOfServers[i], hasLoadPercentageOf(loadPercentages[i]));
        }
    }
}
