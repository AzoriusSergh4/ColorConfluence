package com.cc.test.framework.business;

import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.SeleniumService;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BasePage {

    @Autowired
    SeleniumService seleniumService;
    @Autowired
    FrontendTestCommons testCommons;

    public BasePage() {
    }

    public String getPageTitle() {
        return seleniumService.getPageTitle();
    }

    public void clickOnLogin() {
        seleniumService.findWebElementBy(By.xpath("//div[contains(@class,'login-toolbar')]//a[@routerlink='login']")).click();
    }

    public void goToDeckCreation() {
        this.seleniumService.findWebElementById("deckMenu").click();
        this.seleniumService.findWebElementById("newDeck").click();
    }

    public void goToDeckList() {
        this.seleniumService.findWebElementById("deckMenu").click();
        this.seleniumService.findWebElementById("allDecks").click();
    }

    public abstract boolean checkPageIsLoaded();
}
