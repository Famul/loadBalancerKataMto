package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class serverVmsCountMatcher extends TypeSafeMatcher<Server> {

    private int expectedVmsCount;

    public serverVmsCountMatcher(int expectedVmsCount) {
        this.expectedVmsCount = expectedVmsCount;
    }

    public void describeTo(Description description) {
        description.appendText("server with vms count of ").appendValue(expectedVmsCount);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description description) {
        description.appendText("server with vms count of ").appendValue(item.hasVmsCount());
    }

    @Override
    protected boolean matchesSafely(Server server) {
        return expectedVmsCount == server.hasVmsCount();
    }

    public static serverVmsCountMatcher hasVmsCountOf(int expectedVmsCount) {
        return new serverVmsCountMatcher(expectedVmsCount);
    }

}
