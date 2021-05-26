package com.cc.test.frontend;

import com.cc.test.framework.business.DeckListPage;
import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.Hooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class DeckListFrontendSteps {

    @Autowired
    private FrontendTestCommons testCommons;

    @Autowired
    private Hooks hooks;

    private final DeckListPage deckListPage;

    @Autowired
    public DeckListFrontendSteps(DeckListPage deckListPage) {
        this.deckListPage = deckListPage;
    }

    @When("I search decks by name {string}")
    public void iSearchDecksByName(String name) {
        this.deckListPage.fillName(name);
        this.deckListPage.searchDecks();
    }

    @Then("All decks displayed contains the name {string}")
    public void allDecksDisplayedContainsTheName(String name) {
        this.deckListPage.checkColumn("Name", name);
    }
}
