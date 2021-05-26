package com.cc.test.frontend;

import com.cc.test.framework.business.HomePage;
import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class MainPageInitializationSteps {

    @Autowired
    private FrontendTestCommons testCommons;

    @Autowired
    private HomePage homePage;

    @Autowired
    private Hooks hooks;

    public MainPageInitializationSteps() {
        this.homePage = new HomePage();
    }

    @Given("I get into the Main Page")
    public void iGetIntoTheeMainPage() {
        homePage.goToPage();
    }

    @When("I see the Main Page")
    public void iSeeTheMainPage() {
        //dummy step
    }


    @Then("all features are loaded")
    public void allFeaturesAreLoaded() {
        Assert.assertEquals("ColorConfluence", homePage.getPageTitle());
        hooks.scenario.log("Test Passed");
        testCommons.takeScreenshot(hooks.scenario);
    }
}
