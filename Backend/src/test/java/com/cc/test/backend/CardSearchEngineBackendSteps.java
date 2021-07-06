package com.cc.test.backend;

import com.cc.web.card.CardService;
import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class CardSearchEngineBackendSteps {

    List<Page<CardTranslationProjection>> cardTranslations;
    List<Map<String, String>> currentProperties;

    @Autowired
    CardService cardService;

    @Given("I can connect to the database")
    public void iCanConnectToTheDatabase() {
        //covered in other step
    }

    @When("I search for a card which has {string} in its name")
    public void iSearchForACardWhichHasStringInItsName(String name) {
        currentProperties = new ArrayList<>();
        cardTranslations = new ArrayList<>();
        currentProperties.add(new HashMap<>());
        currentProperties.get(0).put("name", name);
        currentProperties.get(0).put("page", "0");
        cardTranslations.add(cardService.getBasicCardsByCriteria(currentProperties.get(0)));
    }

    @When("I search for cards with specified criteria")
    public void iSearchForCardsWithSpecifiedCriteria(DataTable criteria) {
        currentProperties = new ArrayList<>();
        cardTranslations = new ArrayList<>();
        currentProperties = criteria.asMaps(String.class, String.class);

        for (Map<String, String> map : currentProperties) {
            cardTranslations.add(cardService.getBasicCardsByCriteria(map));
        }
    }

    @Then("There are results")
    public void thereAreResults() {
        Assert.assertTrue(cardTranslations != null && !cardTranslations.isEmpty());
    }

    @And("All results match the specified criteria")
    @Transactional
    public void allResultsMatchTheSpecifiedCriteria() {
        int i = 0;
        for (Page<CardTranslationProjection> page : cardTranslations) {
            for (CardTranslationProjection ct : page) {
                if (currentProperties.get(i).get("name") != null)
                    Assert.assertTrue(ct.getName().toLowerCase().contains(Objects.requireNonNull(currentProperties.get(i).get("name")).toLowerCase()));
                if (currentProperties.get(i).get("type") != null) {
                    String type = currentProperties.get(i).get("type");
                    String[] types = (type.contains(",")) ? type.split(",") : new String[]{type};
                    for (String s : types) {
                        Assert.assertTrue(ct.getCard().getCardType().toLowerCase().contains(Objects.requireNonNull(s.toLowerCase())));
                    }
                }
                if (currentProperties.get(i).get("colors") != null) {
                    String[] colors = currentProperties.get(i).get("colors").replaceAll("[=<>]", "").split("(?!^)");
                    for (String color : colors) {
                        Assert.assertTrue(ct.getCard().getManaCost().toLowerCase().contains(Objects.requireNonNull(color).toLowerCase()));
                    }
                }
                if (currentProperties.get(i).get("manaCost") != null)
                    Assert.assertTrue(ct.getCard().getManaCost().toLowerCase().contains(Objects.requireNonNull(currentProperties.get(i).get("manaCost")).toLowerCase()));
                if (currentProperties.get(i).get("cmc") != null) {
                    statMatch("cmc", currentProperties.get(i).get("cmc"), ct);
                }
                if (currentProperties.get(i).get("power") != null) {
                    statMatch("power", currentProperties.get(i).get("power"), ct);
                }
                if (currentProperties.get(i).get("toughness") != null) {
                    statMatch("toughness", currentProperties.get(i).get("toughness"), ct);
                }
                if (currentProperties.get(i).get("standard") != null)
                    Assert.assertTrue(checkLegality(ct,"Standard", currentProperties.get(i).get("standard")));
                if (currentProperties.get(i).get("duel") != null)
                    Assert.assertTrue(checkLegality(ct,"Duel", currentProperties.get(i).get("duel")));
                if (currentProperties.get(i).get("legacy") != null)
                    Assert.assertTrue(checkLegality(ct,"Legacy", currentProperties.get(i).get("legacy")));
                if (currentProperties.get(i).get("modern") != null)
                    Assert.assertTrue(checkLegality(ct,"Modern", currentProperties.get(i).get("modern")));
                if (currentProperties.get(i).get("vintage") != null)
                    Assert.assertTrue(checkLegality(ct,"Vintage", currentProperties.get(i).get("vintage")));
                if (currentProperties.get(i).get("commander") != null)
                    Assert.assertTrue(checkLegality(ct,"Commander", currentProperties.get(i).get("commander")));
                if (currentProperties.get(i).get("historic") != null)
                    Assert.assertTrue(checkLegality(ct,"Historic", currentProperties.get(i).get("historic")));
                if (currentProperties.get(i).get("pioneer") != null)
                    Assert.assertTrue(checkLegality(ct,"Pioneer", currentProperties.get(i).get("pioneer")));
                if (currentProperties.get(i).get("penny") != null)
                    Assert.assertTrue(checkLegality(ct,"Penny", currentProperties.get(i).get("penny")));
                if (currentProperties.get(i).get("set") != null) {
                    var set = currentProperties.get(i).get("set");
                    Assert.assertTrue(ct.getCardSet().stream().anyMatch(s -> s.getSet().toLowerCase().contains(Objects.requireNonNull(set).toLowerCase())));
                }
                if (currentProperties.get(i).get("rarity") != null)
                    Assert.assertTrue(ct.getCard().getRarity().toLowerCase().contains(Objects.requireNonNull(currentProperties.get(i).get("rarity")).toLowerCase()));
            }

            i++;
        }
    }

    private boolean checkLegality(CardTranslationProjection ct, String legality, String expected) {
        var c = cardService.getCardCCById(ct.getCard().getId());
        return c.getLegalities().stream().filter(l -> l.getFormat().equals(legality)).collect(Collectors.toList()).get(0).getLegality().toLowerCase().contains(Objects.requireNonNull(expected).toLowerCase());
    }

    private void statMatch(String stat, String value, CardTranslationProjection ct) {
        if (value.startsWith(">=")) {
            Assert.assertTrue("Comparing " + stat + " " + value + " : " + statGetter(ct, stat),value.replace(">=", "").toLowerCase().compareTo(Objects.requireNonNull(statGetter(ct, stat))) <= 0);
        } else if (value.startsWith("<=")) {
            Assert.assertTrue(value.replace("<=", "").toLowerCase().compareTo(Objects.requireNonNull(statGetter(ct, stat))) >= 0);
        } else if (value.startsWith(">")) {
            Assert.assertTrue(value.replace(">", "").toLowerCase().compareTo(Objects.requireNonNull(statGetter(ct, stat))) < 0);
        } else if (value.startsWith("<")) {
            Assert.assertTrue(value.replace("<", "").toLowerCase().compareTo(Objects.requireNonNull(statGetter(ct, stat))) > 0);
        } else {
            Assert.assertTrue(Objects.requireNonNull(statGetter(ct, stat)).toLowerCase().contains(Objects.requireNonNull(value.replaceAll("=", "")).toLowerCase()));
        }

    }

    private String statGetter(CardTranslationProjection ct, String stat) {
        switch (stat) {
            case "cmc":
                return String.valueOf(ct.getCard().getCmc());
            case "power":
                return ct.getCard().getPower();
            case "toughness":
                return ct.getCard().getToughness();
            default:
                return null;
        }
    }
}
