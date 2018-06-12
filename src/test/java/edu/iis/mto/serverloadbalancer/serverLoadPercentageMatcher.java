package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class serverLoadPercentageMatcher extends TypeSafeMatcher<Server> {

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
        return server.currentLoadPercentage == expectedLoadPercentage ||
                Math.abs(expectedLoadPercentage - server.currentLoadPercentage) < 0.01d;
    }

    public static serverLoadPercentageMatcher serverWithCurrentLoadPercentageOf(double expectedLoadPercentage) {
        return new serverLoadPercentageMatcher(expectedLoadPercentage);
    }
}
