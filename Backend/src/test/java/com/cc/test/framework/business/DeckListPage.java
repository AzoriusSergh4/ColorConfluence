package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

@Service
@ScenarioScope
public class DeckListPage extends BasePage {

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

    public void fillName(String name) {
        this.seleniumService.typeText(this.seleniumService.findWebElementById("deckName"), name);
    }

    public void fillColors(String colors) {
        var color = colors.split("(?!^)");
        for (String c : color) {
            this.seleniumService.findWebElementBy(By.xpath("//cc-deck-list//mat-checkbox[@value='" + c + "']")).click();
        }
    }

    public void fillFormat(String format) {
        this.seleniumService.selectOption(this.seleniumService.findWebElementBy(By.xpath("//cc-deck-list//mat-select")), format);
    }

    public void searchDecks() {
        this.seleniumService.findWebElementBy(By.xpath("//cc-deck-list//button//span[contains(text(),'Search')]")).click();
        this.seleniumService.waitUpdates();
    }

    public boolean checkColumn(String column, String value) {
        var cells = this.seleniumService.findWebElementsBy(By.xpath("//cc-deck-list//table//tr//td[count(//table//thead//tr//th[contains(text(), '" + column + "')]/preceding-sibling::*)+1]"));
        return cells.stream().allMatch(c -> c.getText().contains(value));
    }
}
