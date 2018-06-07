package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ServerLoadPercentageMatcher extends TypeSafeMatcher<Server> {

    private static final double EPSILON = 0.01d;
    private double expectedLoadPercentage;

    public ServerLoadPercentageMatcher(double expectedLoadPercentage) {
        this.expectedLoadPercentage = expectedLoadPercentage;
    }

    public void describeTo(Description description) {
        description.appendText("server with load percentage of ").appendValue(expectedLoadPercentage);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description description) {
        description.appendText("server with load percentage of ").appendValue(item.currentLoadPercentage);
    }

    @Override
    protected boolean matchesSafely(Server server) {

        return doublesAreEqual(expectedLoadPercentage, server.currentLoadPercentage);
    }

    private boolean doublesAreEqual(double d1, double d2) {
        return d1 == d2 || Math.abs(d1 - d2) < EPSILON;
    }

    public static ServerLoadPercentageMatcher hasCurrentLoadPercentageOF(double expectedLoadPercentage) {
        return new ServerLoadPercentageMatcher(expectedLoadPercentage);
    }

}
