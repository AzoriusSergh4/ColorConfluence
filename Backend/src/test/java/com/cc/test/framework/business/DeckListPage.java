package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

@Service
@ScenarioScope
public class DeckListPage extends BasePage{

    public void goToDeckListCreation() {
        this.seleniumService.findWebElementById("deckMenu").click();
        this.seleniumService.findWebElementById("allDecks").click();
    }

    public void goToFirstDeck() {
        this.seleniumService.findWebElementBy(By.xpath("//table//tbody//tr")).click();
    }

    public boolean checkPageIsLoaded() {
        return seleniumService.isWebElementBy(By.xpath("//cc-deck-list//table//tbody//tr"));
    }
}
