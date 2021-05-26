package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@ScenarioScope
public class CardSearchPage extends BasePage {

    public CardSearchPage() {
    }

    public boolean checkPageIsLoaded() {
        return seleniumService.isWebElementBy(By.xpath("//div[@class='grid-container mtgCard-results']"));
    }

    public boolean checkCardsAreLoaded() {
        return seleniumService.findWebElementsBy(By.xpath("//div[contains(@class,'mtgCard-result-item')]//img")).size() > 0;
    }

    public void clickFirstCard() {
        seleniumService.findWebElementBy(By.xpath("//div[contains(@class,'mtgCard-result-item')]")).click();
    }

    public void openAdvanced() {
        seleniumService.findWebElementBy(By.xpath("//mat-expansion-panel-header")).click();
    }

    public void putData(Map<String, String> data) {
        seleniumService.typeText(seleniumService.findWebElementBy(By.xpath("//input[@formcontrolname='name']")), data.get("name"));
        seleniumService.typeText(seleniumService.findWebElementBy(By.xpath("//input[@formcontrolname='text']")), data.get("text"));
        seleniumService.typeText(seleniumService.findWebElementBy(By.xpath("//input[@formcontrolname='type']")), data.get("type"));
        this.selectColor(data.get("colors"), "<=");
        this.selectStat("CMC", data.get("cmc"), "=");
        this.selectStat("Power", data.get("power"), ">=");
        this.selectStat("Toughness", data.get("toughness"), "<=");
        seleniumService.findWebElementBy(By.xpath(this.getMatSelectXPath("Lang"))).click();
        seleniumService.findWebElementBy(By.xpath("//mat-option[@value='" + data.get("lang") + "']")).click();
        seleniumService.findWebElementBy(By.xpath(this.getMatSelectXPath("Rarity"))).click();
        seleniumService.findWebElementBy(By.xpath("//mat-option[@value='" + data.get("rarity") + "']")).click();
        seleniumService.findWebElementBy(By.xpath("//button[@type='submit']")).click();
    }

    private void selectColor(String value, String criteria) {
        seleniumService.findWebElementBy(By.xpath("//input[@type='checkbox' and @value='" + value + "']/parent::div")).click();
        seleniumService.findWebElementBy(By.xpath(this.getMatSelectXPath("Color criteria"))).click();
        seleniumService.findWebElementBy(By.xpath("//mat-option[@value='" + criteria + "']")).click();
    }

    private void selectStat(String stat, String value, String criteria) {
        seleniumService.findWebElementBy(By.xpath(this.getMatSelectXPath(stat))).click();
        try {
            seleniumService.findWebElementBy(By.xpath("//mat-option[@value='" + criteria + "']")).click();
        } catch (StaleElementReferenceException s) {
            seleniumService.findWebElementBy(By.xpath("//mat-option[@value='" + criteria + "']")).click();
        }
        seleniumService.typeText(seleniumService.findWebElementBy(By.xpath("//mat-label[text()='" + stat + " value" + "']/parent::label/parent::span/preceding-sibling::input")), value);
    }

    private String getMatSelectXPath(String value) {
        return "//mat-label[text()='" + value + "']/parent::label/parent::span/preceding-sibling::mat-select";
    }
}
