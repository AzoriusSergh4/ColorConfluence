package com.cc.test.frontend;

import com.cc.test.framework.business.DeckInfoPage;
import com.cc.test.framework.business.DeckListPage;
import com.cc.test.framework.business.HomePage;
import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class DeckAnalysisSteps {

    @Autowired
    private FrontendTestCommons testCommons;

    @Autowired
    private Hooks hooks;

    private final HomePage homePage;

    private final DeckListPage deckListPage;

    private final DeckInfoPage deckInfoPage;

    @Autowired
    public DeckAnalysisSteps(HomePage homePage, DeckListPage deckListPage, DeckInfoPage deckInfoPage) {
        this.homePage = homePage;
        this.deckListPage = deckListPage;
        this.deckInfoPage = deckInfoPage;
    }

    @And("I go the Deck List Page")
    public void iGoTheDeckListPage() {
        this.deckListPage.goToDeckListCreation();
    }

    @And("I go into a deck")
    public void iGoIntoADeck() {
        Assert.assertTrue(deckListPage.checkPageIsLoaded());
        this.deckListPage.goToFirstDeck();
    }

    @And("The deck page is rendered")
    public void theDeckPageIsRendered() {
        Assert.assertTrue(deckInfoPage.checkPageIsLoaded());
    }

    @When("I get into the analysis tab")
    public void iGetIntoTheAnalysisTab() {
        this.deckInfoPage.goToTab("Analytics");

    }

    @Then("the analysis tab is loaded and I can see the charts")
    public void theAnalysisTabIsLoadedAndICanSeeTheCharts() {
        Assert.assertTrue(this.deckInfoPage.checkAnalysis());
        testCommons.takeScreenshot(hooks.scenario);
    }
}
