package com.cc.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.cc.ColorConfluenceApplication;
import com.cc.card.CardService;
import com.cc.entity.CardCC;
import com.cc.entity.CardTranslation;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@ContextConfiguration(classes = ColorConfluenceApplication.class)
public class StepDefinitions {
	@Autowired 
	private CardService cardService;
	
	private String name;
	List<CardTranslation> cards;
	/**
	 * Scenario: Search by name
	 */
	@Given("Card name Azorius")
	public void card_name_Azorius() {
	    this.name = "Azorius";
	}

	@When("I search that name")
	public void i_search_that_name() {
	   cards = cardService.getByName(this.name);
	}

	@Then("I see all cards with Azorius in its name")
	public void i_see_all_cards_with_Azorius_in_its_name() {
	    boolean ok = true;
	    if(cards.size()==0) {
	    	ok = false;
	    }
	    for(CardTranslation card : cards) {
	    	if(!ok) {
	    		break;
	    	}
	    	if(!card.getName().contains(this.name)) ok = false;	    	
	    }
	    assertTrue(ok);
	}
}
