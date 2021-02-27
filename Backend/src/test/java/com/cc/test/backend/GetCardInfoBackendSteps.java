package com.cc.test.backend;

import com.cc.web.card.CardService;
import com.cc.web.entity.CardTranslation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class GetCardInfoBackendSteps {

    @Autowired
    CardService cardService;

    CardTranslation cardTranslation;

    @When("I search a card by its id with id {long}")
    public void iSearchACardByItsId(long id) {
        cardTranslation = cardService.getCardById(id);
    }

    @Then("There is a result")
    public void thereIsAResult() {
        Assert.assertNotNull(cardTranslation);
    }

    @And("All the information is loaded")
    public void allTheInformationIsLoaded() {
        Assert.assertNotNull(cardTranslation.getCard());
        Assert.assertNotNull(cardTranslation.getName());
        Assert.assertNotNull(cardTranslation.getCardSet());
    }
}
