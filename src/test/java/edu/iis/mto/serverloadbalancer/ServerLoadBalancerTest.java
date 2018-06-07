package edu.iis.mto.serverloadbalancer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
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

    private Matcher<? super Server> hasCurrentLoadPercentageOF(double expectedLoadPercentage) {
        return new ServerLoadPercentageMatcher(expectedLoadPercentage);
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

    private ServerBuilder server() {
        return new ServerBuilder();
    }

}
