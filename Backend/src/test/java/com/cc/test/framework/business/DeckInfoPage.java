package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

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
}
