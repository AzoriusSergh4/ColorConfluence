package com.cc.test.framework.selenium;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

    public Scenario scenario;

    @Autowired
    private SeleniumService seleniumService;

    @Before(value = "@Frontend")
    public void beforeScenario(Scenario scenario) {
        this.scenario = scenario;
        seleniumService.startDriver();
    }

    @After(value = "@Frontend")
    public void afterScenario(Scenario scenario) {
        seleniumService.stopDriver();
    }

}
