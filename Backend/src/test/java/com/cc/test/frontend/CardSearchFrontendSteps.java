package com.cc.test.frontend;

import com.cc.test.framework.business.CardSearchPage;
import com.cc.test.framework.business.HomePage;
import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.Hooks;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class CardSearchFrontendSteps {

    @Autowired
    private FrontendTestCommons testCommons;

    @Autowired
    private HomePage homePage;

    @Autowired
    private CardSearchPage cardSearchPage;

    @Autowired
    private Hooks hooks;

    public CardSearchFrontendSteps() {
    }

    @When("I enter the name of a card")
    public void iEnterTheNameOfACard() {
        homePage.searchCard("Dimir");
    }

    @Then("The results page is loaded")
    public void theResultsPageIsLoaded() {
        Assert.assertTrue(cardSearchPage.checkPageIsLoaded());
    }

    @And("I can see all results in block")
    public void iCanSeeAllResultsInBlock() {
        Assert.assertTrue(cardSearchPage.checkCardsAreLoaded());
        testCommons.takeScreenshot(hooks.scenario);
    }

    @When("I go into the Advance Search Page")
    public void iGoIntoTheAdvanceSearchPage() {
        homePage.goToSearchPage();
    }

    @And("I enter all the information")
    public void iEnterAllTheInformation(DataTable criteria) {
        cardSearchPage.openAdvanced();
        List<Map<String, String>> list = criteria.asMaps(String.class, String.class);
        cardSearchPage.putData(list.get(0));
    }

    @And("The Advance Search Page is loaded")
    public void theAdvanceSearchPageIsLoaded() {
        Assert.assertTrue(cardSearchPage.checkPageIsLoaded());
    }
}
