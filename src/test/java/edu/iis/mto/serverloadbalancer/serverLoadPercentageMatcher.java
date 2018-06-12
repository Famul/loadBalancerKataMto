package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class serverLoadPercentageMatcher extends TypeSafeMatcher<Server> {

    private static final double EPSILON = 0.01d;
    private double expectedLoadPercentage;

    public serverLoadPercentageMatcher(double expectedLoadPercentage) {
        this.expectedLoadPercentage = expectedLoadPercentage;
    }

    public void describeTo(Description description) {
        description.appendText("server with current load percentage of ").appendValue(expectedLoadPercentage);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description description) {
        description.appendText("server with current load percentage of ").appendValue(item.currentLoadPercentage);
    }

    @Override
    protected boolean matchesSafely(Server server) {
        return doublesAreEqual(server.currentLoadPercentage, expectedLoadPercentage);
    }

    private boolean doublesAreEqual(double d1, double d2) {
        return d1 == d2 || Math.abs(d2 - d1) < EPSILON;
    }

    public static serverLoadPercentageMatcher serverWithCurrentLoadPercentageOf(double expectedLoadPercentage) {
        return new serverLoadPercentageMatcher(expectedLoadPercentage);
    }
}
