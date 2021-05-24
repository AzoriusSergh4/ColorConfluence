package com.cc.test.framework.business;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

@Service
@ScenarioScope
public class DeckCreationPage extends BasePage{

    public void goToDeckCreation() {
        this.seleniumService.findWebElementById("deckMenu").click();
        this.seleniumService.findWebElementById("newDeck").click();
    }

    public boolean checkPageIsLoaded() {
        return this.seleniumService.isWebElementBy(By.xpath("//cc-deck-creation//div[@class='grid-container grid-deck']"));
    }

    public void fillDeckInfo(JsonObject deckForm) {
        this.seleniumService.typeText(this.seleniumService.findWebElementById("nameInput"), deckForm.get("name").getAsString());
        if(deckForm.get("format").getAsJsonObject() != null) this.seleniumService.selectOption(this.seleniumService.findWebElementById("formatInput"), deckForm.get("format").getAsJsonObject().get("name").getAsString());
        this.seleniumService.typeText(this.seleniumService.findWebElementById("commentsInput"), deckForm.get("comments").getAsString());

        //Drag and drop cards
        WebElement searchBar = this.seleniumService.findWebElementById("searchInput");
        WebElement quantity = this.seleniumService.findWebElementBy(By.xpath("//div[@gdarea='searchBar']//input[@type='number']"));
        for(JsonElement c : deckForm.get("main").getAsJsonArray()){
            JsonObject card = c.getAsJsonObject();
            this.seleniumService.clearText(quantity);
            this.seleniumService.typeText(quantity, card.get("quantity").getAsString());
            this.seleniumService.clearText(searchBar);
            this.seleniumService.typeText(searchBar, card.get("name").getAsString());
            this.seleniumService.waitUpdates();
            this.seleniumService.dragAndDrop(
                    this.seleniumService.findWebElementBy(By.xpath("//mat-panel-title[contains(text(),'" + card.get("name").getAsString() + "')]/ancestor::div[@cdkdrag]")),
                    this.seleniumService.findWebElementById("deckList"));
        }
        for(JsonElement c : deckForm.get("sideboard").getAsJsonArray()){
            JsonObject card = c.getAsJsonObject();
            this.seleniumService.clearText(quantity);
            this.seleniumService.typeText(quantity, card.get("quantity").getAsString());
            this.seleniumService.clearText(searchBar);
            this.seleniumService.typeText(searchBar, card.get("name").getAsString());
            this.seleniumService.waitUpdates();
            this.seleniumService.dragAndDrop(
                    this.seleniumService.findWebElementBy(By.xpath("//mat-panel-title[contains(text(),'" + card.get("name").getAsString() + "')]/ancestor::div[@cdkdrag]")),
                    this.seleniumService.findWebElementById("sideboardList"));
        }
    }

    public void saveDeck() {
        this.seleniumService.findWebElementById("saveButton").click();
        this.seleniumService.findWebElementById("saveDialog").click();
    }
}
