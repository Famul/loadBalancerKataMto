package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ServerVmsCounterMatcher extends TypeSafeMatcher<Server> {

    private int expectedVmsCount;

    public ServerVmsCounterMatcher(int expectedVmsCount) {
        this.expectedVmsCount = expectedVmsCount;
        // TODO Auto-generated constructor stub
    }

    public void describeTo(Description description) {
        description.appendText("a server with vms count of ").appendValue(expectedVmsCount);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description mismatchDescription) {
        mismatchDescription.appendText("a server with vms count of ").appendValue(item.countVms());
    }

    @Override
    protected boolean matchesSafely(Server server) {
        return expectedVmsCount == server.countVms();
    }

}
