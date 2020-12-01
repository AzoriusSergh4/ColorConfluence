package com.cc.test.frontend;

import com.cc.test.framework.business.CardSearchPage;
import com.cc.test.framework.business.HomePage;
import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.Hooks;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class CardSearchNameFrontendSteps{

    @Autowired
    private FrontendTestCommons testCommons;

    @Autowired
    private HomePage homePage;

    @Autowired
    private CardSearchPage cardSearchPage;

    @Autowired
    private Hooks hooks;

    public CardSearchNameFrontendSteps() {
    }

    @When("I enter the name of a card")
    public void iEnterTheNameOfACard() {
        homePage.searchCard("Dimir");
    }

    @Then("The results page is loaded")
    public void theResultsPageIsLoaded() {
        Assert.assertTrue(cardSearchPage.checkSiteIsLoaded());
    }

    @And("I can see all results in block")
    public void iCanSeeAllResultsInBlock() {
        Assert.assertTrue(cardSearchPage.checkCardsAreLoaded());
        testCommons.takeScreenshot(hooks.scenario);
    }
}
