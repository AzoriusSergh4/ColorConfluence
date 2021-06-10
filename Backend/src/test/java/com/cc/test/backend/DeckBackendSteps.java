package com.cc.test.backend;

import com.cc.security.user.User;
import com.cc.security.user.UserRepository;
import com.cc.web.deck.DeckService;
import com.cc.web.entity.Deck;
import com.cc.web.entity.payloads.DeckForm;
import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class DeckBackendSteps {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeckService deckService;

    private User testUser;

    private long id;

    @And("I am logged in")
    public void iAmLoggedIn() {
        this.testUser = userRepository.findByEmailOrUsername("ColorConfluenceTest", "ColorConfluenceTest");
    }

    @When("I add the deck information")
    @Transactional
    public void iAddTheDeckInformation() {
        Reader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("inputs/DeckCreation.json")));
        DeckForm deckForm = new Gson().fromJson(reader, DeckForm.class);
        this.id = this.deckService.saveDeck(this.testUser, deckForm).getId();
    }

    @Then("the deck is stored successfully")
    public void theDeckIsStoredSuccessfully() {
        Deck deck = this.deckService.getDeckById(this.id);
        Assert.assertNotNull(deck);
        this.deckService.deleteDeck(deck);
    }


}
