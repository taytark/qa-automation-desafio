package hooks;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    private BaseTest baseTest = new BaseTest();

    @Before
    public void beforeScenario() {
        baseTest.startDriver();
    }

    @After
    public void afterScenario() {
        baseTest.quitDriver();
    }
}