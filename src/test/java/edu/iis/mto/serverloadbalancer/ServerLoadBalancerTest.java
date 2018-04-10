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
	public void loadBalanceOfServerWithoutVMsIsEmpty() {
	    Serwer theSerwer = a(serwer().withCapacity(1));
	    
	    balance(aListOfServersWith(theSerwer), anEmptyListOfVms());
	    assertThat(theSerwer, hasCorrectPercentageOf(0.0d));
	}
    private Matcher<? super Serwer> hasCorrectPercentageOf(double expectedLoadPercentage) {
        return new CurrentLoadPercentageMatcher(expectedLoadPercentage);
    }

    private void balance(Serwer[] serwers, VM[] vms) {
        new ServerLoadBalancer().balance(serwers, vms);
    }

    private VM[] anEmptyListOfVms() {
        return new VM[0];
    }

    private Serwer[] aListOfServersWith(Serwer... Serwers) {
        return Serwers;
    }

    private Serwer a(ServerBuilder serverBuilder) {
        
        return serverBuilder.build();
    }

    private ServerBuilder serwer() {
        // TODO Auto-generated method stub
        return new ServerBuilder();
    }

	
}
/*
ctrl + .                -- do najblizszego bledu
ctrl + 1                -- mozliwe rozwiazania bledu
ctrl + F6               -- przelaczanie okien
ctrl + d                -- usun linie
alt + shift + r         -- rename
ctrl + shift + l        -- list of hotkeys    
alt + shift + x  > t    -- start test
*/