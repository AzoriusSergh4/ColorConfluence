package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@ScenarioScope
public class DeckInfoPage extends BasePage{


    public void goToTab(String tab) {
        this.seleniumService.findWebElementBy(By.xpath("//mat-tab-group//div[@class='mat-tab-labels']//div[@role='tab']//div[contains(text(), '" + tab + "')]")).click();
    }

    public boolean checkAnalysis() {
        return seleniumService.findWebElementsBy(By.xpath("//mat-tab-body//mat-card//mat-card-content//canvas")).size() == 4;
    }

    public boolean checkProbabilities() {
        return seleniumService.findWebElementsBy(By.xpath("//mat-card//tbody/tr//td[contains(text(),'%')]")).size() > 0 ;
    }

    public boolean checkPageIsLoaded() {
        return this.seleniumService.isWebElementBy(By.xpath("//cc-deck//mat-card-title[text()=' Main ']/ancestor::mat-card//mat-card-content//table//tbody//tr"));
    }

    public boolean checkOpeningHand() {
        this.seleniumService.waitUpdates();
        return this.seleniumService.findWebElementsBy(By.xpath("//mat-tab-group//mat-tab-body//img")).size() == 7;
    }

    public boolean checkMulligan() {
        var urls = new ArrayList<String>();
        var cards = this.seleniumService.findWebElementsBy(By.xpath("//mat-tab-group//mat-tab-body//img"));
        for(WebElement e : cards) {
            urls.add(e.getAttribute("src"));
        }
        this.seleniumService.findWebElementBy(By.xpath("//mat-tab-group//mat-tab-body//button//span[text()='Mulligan']")).click();
        this.seleniumService.waitUpdates();
        var urls2 = new ArrayList<String>();
        cards = this.seleniumService.findWebElementsBy(By.xpath("//mat-tab-group//mat-tab-body//img"));
        for(WebElement e : cards) {
            urls2.add(e.getAttribute("src"));
        }
        return !urls.equals(urls2);
    }
}
