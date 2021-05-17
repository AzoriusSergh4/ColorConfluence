package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

@Service
@ScenarioScope
public class DeckInfoPage extends BasePage{


    public boolean checkPageIsLoaded() {
        return this.seleniumService.isWebElementBy(By.xpath("//cc-deck//mat-card-title[text()=' Main ']/ancestor::mat-card//mat-card-content//table//tbody//tr"));
    }
}
