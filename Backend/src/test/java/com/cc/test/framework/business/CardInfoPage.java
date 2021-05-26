package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ScenarioScope
public class CardInfoPage extends BasePage {

    public CardInfoPage() {

    }

    public boolean checkPageIsLoaded() {
        return seleniumService.isWebElementBy(By.xpath("//mat-card[contains(@class,'cc-card-info')]"));
    }

    public boolean checkLanguages() {
        List<WebElement> languages = seleniumService.findWebElementsBy(By.xpath("//div[@gdarea='lang']//button"));
        if (languages.get(0).isEnabled()) return false;
        for (int i = 1; i < languages.size(); i++) {
            if (!languages.get(i).isEnabled()) return false;
        }
        return true;
    }

    public boolean checkInfo() {
        if (seleniumService.findWebElementById("data-type").getText().equals("")) return false;
        if (seleniumService.findWebElementById("collection-rarity").getText().equals("")) return false;
        return !seleniumService.findWebElementById("collection-legality").getText().equals("");
    }

    public boolean checkSets() {
        List<WebElement> sets = seleniumService.findWebElementsBy(By.xpath("//div[@id='collection-sets']//button"));
        if (sets.get(0).isEnabled()) return false;
        for (int i = 1; i < sets.size(); i++) {
            if (!sets.get(i).isEnabled()) return false;
        }
        return true;
    }
}
