package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@ScenarioScope
public class DeckInfoPage extends BasePage {


    /**
     * Clicks on the given tab
     *
     * @param tab the name of the tab
     */
    public void goToTab(String tab) {
        this.seleniumService.findWebElementBy(By.xpath("//mat-tab-group//div[@class='mat-tab-labels']//div[@role='tab']//div[contains(text(), '" + tab + "')]")).click();
    }

    /**
     * Check Analysis tab is correct
     *
     * @return true if the graphs are loaded, false in other case
     */
    public boolean checkAnalysis() {
        return seleniumService.findWebElementsBy(By.xpath("//mat-tab-body//mat-card//mat-card-content//canvas")).size() == 4;
    }

    /**
     * Check if the Probabilities tab is correct
     *
     * @return true if data and probabilities are loaded, false in other case
     */
    public boolean checkProbabilities() {
        return seleniumService.findWebElementsBy(By.xpath("//mat-card//tbody/tr//td[contains(text(),'%')]")).size() > 0;
    }

    /**
     * Check if the Deck Info Page is loaded
     *
     * @return true if the page is loaded, false in other case
     */
    public boolean checkPageIsLoaded() {
        return this.seleniumService.isWebElementBy(By.xpath("//cc-deck//mat-card-title[text()=' Main ']/ancestor::mat-card//mat-card-content//table//tbody//tr"));
    }

    /**
     * Check if the Opening Hand tab is correct
     *
     * @return true if there are 7 cards displayed, false in other case
     */
    public boolean checkOpeningHand() {
        this.seleniumService.waitUpdates();
        return this.seleniumService.findWebElementsBy(By.xpath("//mat-tab-group//mat-tab-body//img")).size() > 0;
    }

    /**
     * Check if the mulligan is correct
     *
     * @return true if the cards are different, false in other case
     */
    public boolean checkMulligan() {
        var urls = new ArrayList<String>();
        var cards = this.seleniumService.findWebElementsBy(By.xpath("//mat-tab-group//mat-tab-body//img"));
        for (WebElement e : cards) {
            urls.add(e.getAttribute("src"));
        }
        this.seleniumService.findWebElementBy(By.xpath("//mat-tab-group//mat-tab-body//button//span[text()='Mulligan']")).click();
        this.seleniumService.waitUpdates();
        var urls2 = new ArrayList<String>();
        cards = this.seleniumService.findWebElementsBy(By.xpath("//mat-tab-group//mat-tab-body//img"));
        for (WebElement e : cards) {
            urls2.add(e.getAttribute("src"));
        }
        return !urls.equals(urls2);
    }

    /**
     * Check if the Export tab is correct
     *
     * @return true if the list is displayed, false in other case
     */
    public boolean checkExport() {
        return !this.seleniumService.findWebElementBy(By.xpath("//mat-tab-group//mat-tab-body//textarea")).getAttribute("value").equals("");
    }
}
