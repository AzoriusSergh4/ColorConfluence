package com.cc.test.backend;

import com.cc.web.card.CardService;
import com.cc.web.entity.projection.CardTranslationProjection;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CardSearchEngineSearchByNameSteps {

    List<CardTranslationProjection> cardTranslations = new ArrayList<>();
    String currentName;

    @Autowired
    CardService cardService;


    @Given("I can connect to the database")
    public void iCanConnectToTheDatabase() {
        //covered in other step
    }

    @When("I search for a card which has {string} in its name")
    public void iSearchForACardWhichHasStringInItsName(String name) {
        currentName = name;
        cardTranslations = cardService.getBasicCardsByTranslationName(name);
    }

    @Then("There are results")
    public void thereAreResults() {
        Assert.assertTrue(cardTranslations != null && cardTranslations.size() > 0);
    }

    @And("All results match the specified criteria")
    public void allResultsMatchTheSpecifiedCriteria() {
        for(CardTranslationProjection ct : cardTranslations){
            Assert.assertTrue(ct.getName().toLowerCase().contains(currentName.toLowerCase()));
        }
    }

}
