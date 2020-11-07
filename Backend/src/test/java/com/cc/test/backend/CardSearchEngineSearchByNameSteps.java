package com.cc.test.backend;

import com.cc.web.card.CardService;
import com.cc.web.entity.CardTranslation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CardSearchEngineSearchByNameSteps {

    List<CardTranslation> cardTranslations = new ArrayList<>();
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
        cardTranslations = cardService.getByTranslationName(name);
    }

    @Then("There are results")
    public void thereAreResults() {
        Assert.assertTrue(cardTranslations != null && cardTranslations.size() > 0);
    }

    @And("All results match the specified criteria")
    public void allResultsMatchTheSpecifiedCriteria() {
        for(CardTranslation ct : cardTranslations){
            Assert.assertTrue(ct.getName().toLowerCase().contains(currentName.toLowerCase()));
        }
    }

}
