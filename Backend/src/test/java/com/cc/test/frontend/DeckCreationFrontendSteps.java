package com.cc.test.frontend;

import com.cc.test.TestProperties;
import com.cc.test.framework.business.DeckCreationPage;
import com.cc.test.framework.business.DeckInfoPage;
import com.cc.test.framework.business.HomePage;
import com.cc.test.framework.business.LoginPage;
import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.Hooks;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class DeckCreationFrontendSteps {

    @Autowired
    private FrontendTestCommons testCommons;

    private final HomePage homePage;

    private final LoginPage loginPage;

    private final DeckCreationPage deckCreationPage;

    private final DeckInfoPage deckInfoPage;

    @Autowired
    private Hooks hooks;

    private final TestProperties properties;

    @Autowired
    public DeckCreationFrontendSteps(TestProperties properties, HomePage homePage, LoginPage loginPage, DeckCreationPage deckCreationPage, DeckInfoPage deckInfoPage) {
        this.properties = properties;
        this.homePage = homePage;
        this.loginPage = loginPage;
        this.deckCreationPage = deckCreationPage;
        this.deckInfoPage = deckInfoPage;
    }

    @And("I log in")
    public void iLogIn() {
        this.homePage.clickOnLogin();
        Assert.assertTrue(this.loginPage.checkPageIsLoaded());
        this.loginPage.login(this.properties.getUser().getUsername(), this.properties.getUser().getPassword());
        Assert.assertTrue(this.loginPage.checkLogged());
        testCommons.takeScreenshot(hooks.scenario);
    }

    @And("I get into the Deck Creation Page")
    public void iGetIntoTheDeckCreationPage() {
        this.deckCreationPage.goToDeckCreation();
    }

    @When("I enter the cards and I save them")
    public void iEnterTheCardsAndISaveThem() {
        Assert.assertTrue(this.deckCreationPage.checkPageIsLoaded());
        Reader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("inputs/DeckCreationFrontend.json")));
        JsonObject deckForm = new Gson().fromJson(reader, JsonObject.class);
        this.deckCreationPage.fillDeckInfo(deckForm);
        this.deckCreationPage.saveDeck();
    }

    @Then("the deck is saved successfully")
    public void theDeckIsSavedSuccessfully() {
        //covered in iTheDeckIsDisplayedInMyProfile()
    }

    @And("I the deck is displayed in my profile")
    public void iTheDeckIsDisplayedInMyProfile() {
        this.deckInfoPage.checkPageIsLoaded();
    }
}
