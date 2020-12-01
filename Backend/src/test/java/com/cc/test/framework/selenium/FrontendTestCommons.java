package com.cc.test.framework.selenium;

import io.cucumber.java.Scenario;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@ScenarioScope
public class FrontendTestCommons extends TestCommons {

    @Autowired
    private SeleniumService seleniumService;

    public void takeScreenshot(Scenario scenario){
        String screenshotName = seleniumService.getPageTitle() + "_" + new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        scenario.attach(seleniumService.takeScreenshot(),"image/png",screenshotName);
    }

}
