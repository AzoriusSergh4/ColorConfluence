package com.cc.test.framework;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@ScenarioScope
public class FrontendTestCommons extends TestCommons{

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

    public void takeScreenshot(){
        String screenshotName = seleniumService.getPageTitle() + "_" + new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        this.scenario.attach(seleniumService.takeScreenshot(),"image/png",screenshotName);
    }

}
