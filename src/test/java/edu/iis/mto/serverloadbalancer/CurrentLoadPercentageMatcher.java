package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Serwer> {

    private double expectedLoadPercentage;

    public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
        this.expectedLoadPercentage = expectedLoadPercentage;
    }

    public void describeTo(Description description) {
        description.appendText("a server of load percentage of ").appendValue(expectedLoadPercentage);
    }

    @Override
    protected boolean matchesSafely(Serwer serwer) {
        return expectedLoadPercentage == serwer.currentLoadPercentage 
                || Math.abs(expectedLoadPercentage - serwer.currentLoadPercentage) < 0.01d;
    }

}
