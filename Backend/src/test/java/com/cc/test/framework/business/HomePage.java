package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

@Service
@ScenarioScope
public class HomePage extends BasePage {

    public HomePage() {

    }

    public boolean checkPageIsLoaded() {
        return seleniumService.isWebElementBy(By.xpath("//cc-main-screen//div"));
    }

    public void searchCard(String name) {
        seleniumService.typeText(seleniumService.findWebElementById("card-search-input"), name);
    }

    public void goToSearchPage() {
        seleniumService.findWebElementById("search-cards-link").click();
    }

    public void goToPage() {
        seleniumService.goToMainPage();
    }
}
