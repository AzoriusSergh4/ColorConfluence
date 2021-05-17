package com.cc.test.framework.business;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

@Service
@ScenarioScope
public class LoginPage extends BasePage{

    public boolean checkPageIsLoaded(){
        return seleniumService.isWebElementBy(By.xpath("//div[@class='grid-container grid-container-login']"));
    }

    public void login(String user, String password) {
        this.seleniumService.typeText(seleniumService.findWebElementBy(By.xpath("//input[@formcontrolname='username']")), user);
        this.seleniumService.typeText(seleniumService.findWebElementBy(By.xpath("//input[@formcontrolname='password']")), password);
        this.seleniumService.findWebElementById("loginButton").click();
    }

    public boolean checkLogged() {
        return this.seleniumService.isWebElementBy(By.xpath("//mat-icon[text()='person']"));
    }
}
