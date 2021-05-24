package com.cc.test.frontend;

import com.cc.test.framework.business.DeckInfoPage;
import com.cc.test.framework.business.DeckListPage;
import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class DeckInfoFrontendSteps {

    @Autowired
    private FrontendTestCommons testCommons;

    @Autowired
    private Hooks hooks;

    private final DeckListPage deckListPage;

    private final DeckInfoPage deckInfoPage;

    @Autowired
    public DeckInfoFrontendSteps(DeckListPage deckListPage, DeckInfoPage deckInfoPage) {
        this.deckListPage = deckListPage;
        this.deckInfoPage = deckInfoPage;
    }

    @And("I go to the Deck List Page")
    public void iGoToTheDeckListPage() {
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

    @Then("the analysis tab is loaded and I can see the charts")
    public void theAnalysisTabIsLoadedAndICanSeeTheCharts() {
        Assert.assertTrue(this.deckInfoPage.checkAnalysis());
        testCommons.takeScreenshot(hooks.scenario);
    }

    @When("I get into the {string} tab")
    public void iGetIntoTheTab(String tab) {
        this.deckInfoPage.goToTab(tab);
    }

    @Then("the probabilities tab is loaded and I can see the percentages")
    public void theProbabilitiesTabIsLoadedAndICanSeeThePercentages() {
        Assert.assertTrue(this.deckInfoPage.checkProbabilities());
        testCommons.takeScreenshot(hooks.scenario);
    }

    @Then("the opening hand tab is loaded and I can see seven cards")
    public void theOpeningHandTabIsLoadedAndICanSeeSevenCards() {
        Assert.assertTrue(this.deckInfoPage.checkOpeningHand());
        testCommons.takeScreenshot(hooks.scenario);
        Assert.assertTrue(this.deckInfoPage.checkMulligan());
        testCommons.takeScreenshot(hooks.scenario);
    }
}
