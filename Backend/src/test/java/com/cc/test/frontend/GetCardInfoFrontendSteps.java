package com.cc.test.frontend;

import com.cc.test.framework.business.CardInfoPage;
import com.cc.test.framework.business.CardSearchPage;
import com.cc.test.framework.business.HomePage;
import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class GetCardInfoFrontendSteps {

    @Autowired
    private FrontendTestCommons testCommons;

    @Autowired
    private HomePage homePage;

    @Autowired
    private CardSearchPage cardSearchPage;

    @Autowired
    private CardInfoPage cardInfoPage;

    @Autowired
    private Hooks hooks;

    @When("I search for a card with name {string}")
    public void iSearchForACardWithName(String name) {
        homePage.searchCard(name);
    }

    @Then("The card page is loaded")
    public void theCardPageIsLoaded() {
        Assert.assertTrue(cardInfoPage.checkPageIsLoaded());
        testCommons.takeScreenshot(hooks.scenario);
    }

    @And("I can see all the information from the card")
    public void iCanSeeAllTheInformationFromTheCard() {
        Assert.assertTrue(cardInfoPage.checkLanguages());
        Assert.assertTrue(cardInfoPage.checkInfo());
        Assert.assertTrue(cardInfoPage.checkSets());
    }

    @And("I click on a card")
    public void iClickOnACard() {
        cardSearchPage.clickFirstCard();
    }
}
