package edu.iis.mto.serverloadbalancer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Assert;
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

        Assert.assertThat(theServer, hasCurrentLoadPercentageOf(0.0d));
    }

    private Matcher<? super Server> hasCurrentLoadPercentageOf(double expectedLoadPercentage) {
        return new serverLoadPercentageMatcher(expectedLoadPercentage);
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

    private Server[] aListOfServersWith(Server... servers) {
        return servers;
    }

    private ServerBuilder server() {
        // TODO Auto-generated method stub
        return new ServerBuilder();
    }

}
