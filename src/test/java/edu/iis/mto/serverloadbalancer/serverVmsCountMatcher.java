package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class serverVmsCountMatcher extends TypeSafeMatcher<Server> {

    private int expectedCountOfVms;

    public serverVmsCountMatcher(int expectedCountOfVms) {
        this.expectedCountOfVms = expectedCountOfVms;
    }

    public void describeTo(Description description) {
        description.appendText("the server having count of vms of ").appendValue(expectedCountOfVms);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description description) {
        description.appendText("the server having count of vms of ").appendValue(item.countOfVms());
    }

    @Override
    protected boolean matchesSafely(Server server) {
        return expectedCountOfVms == server.countOfVms();
    }

}
