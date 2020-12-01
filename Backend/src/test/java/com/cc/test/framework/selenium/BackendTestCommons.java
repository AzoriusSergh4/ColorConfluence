package com.cc.test.framework.selenium;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.ScenarioScope;

@ScenarioScope
public class BackendTestCommons extends TestCommons {

    @Before(value = "@Backend")
    public void beforeScenario(Scenario scenario){

    }

    @After(value = "@Backend")
    public void afterScenario(Scenario scenario) {

    }
}
