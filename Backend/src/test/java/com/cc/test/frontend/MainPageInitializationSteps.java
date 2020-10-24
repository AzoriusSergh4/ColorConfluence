package com.cc.test.frontend;

import com.cc.test.framework.CcWebElement;
import com.cc.test.framework.FrontendTestCommons;
import com.cc.test.framework.SeleniumService;
import com.cc.test.framework.TestCommons;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

public class MainPageInitializationSteps {

    @Autowired
    private SeleniumService seleniumService;

    @Autowired
    private FrontendTestCommons testCommons;

    @Given("I get into the Main Page")
    public void iGetIntoTheeMainPage() {
        seleniumService.goToMainPage();
    }

    @When("I see the Main Page")
    public void iSeeTheMainPage() {
        //dummy step
    }


    @Then("all features are loaded")
    public void allFeaturesAreLoaded() {
        Assert.assertEquals("ColorConfluence", seleniumService.getPageTitle());
        testCommons.scenario.log("Test Passed");
        testCommons.takeScreenshot();
    }
}
