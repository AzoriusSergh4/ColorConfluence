package com.cc.test.framework.selenium;

import com.cc.test.TestProperties;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Component
@ScenarioScope
public class SeleniumService {

    private WebDriver wDriver;

    private final TestProperties properties;

    private JSWaiter waiter;

    @Autowired
    public SeleniumService(TestProperties properties){
        this.properties = properties;
        waiter = new JSWaiter();

    }

    public void goToMainPage() {
        if(properties.isDebug()){
            wDriver.get(properties.getUrl().getDev());
        }else{
            wDriver.get(properties.getUrl().getProd());
        }
    }

    public String getPageTitle(){
        return wDriver.getTitle();
    }


    public WebElement findWebElementBy(By by){
        try {
            return (WebElement) wDriver.findElement(by);
        } catch (NoSuchElementException n){
            n.printStackTrace();
            throw n;
        }
    }

    public WebElement findWebElementById(String id){
        try {
            return wDriver.findElement(By.id(id));
        } catch (NoSuchElementException n){
            n.printStackTrace();
            throw n;
        }
    }

    public List<WebElement> findWebElementsBy(By by){
        try {
            return wDriver.findElements(by);
        } catch (NoSuchElementException n){
            n.printStackTrace();
            throw n;
        }
    }

    public boolean isWebElementBy(By by){
        return isWebElementBy(by, null);
    }

    public boolean isWebElementBy(By by, WebElement parent){
        WebElement element;
            try {
                element = (parent == null) ? wDriver.findElement(by) : parent.findElement(by);
            }catch (NoSuchElementException n){
                return false;
            }
        return element != null;
    }

    public void clearText(WebElement element){
        element.clear();
    }

    public void typeText(WebElement element, String text){
        Actions actions = new Actions(wDriver);
        actions.sendKeys(element, text).perform();
        actions.sendKeys(element, Keys.ENTER).perform();
    }

    public void selectOption(WebElement select, String option){
        select.click();
        wDriver.findElement(By.xpath("//span[text()='" + option + "']/parent::mat-option")).click();
    }

    public void waitUpdates() {
        waiter.waitAllRequest();
    }

    public void dragAndDrop(WebElement from, WebElement to) {
        int x = to.getLocation().x;
        int y = to.getLocation().y;

        Actions actions = new Actions(wDriver);
        actions.moveToElement(from)
                .pause(Duration.ofMillis(500))
                .clickAndHold(from)
                .pause(Duration.ofMillis(500))
                .moveByOffset(x, y)
                .moveToElement(to)
                .pause(Duration.ofMillis(500))
                .release().build().perform();
    }

    public byte[] takeScreenshot(){
        TakesScreenshot ts = (TakesScreenshot) wDriver;
        return ts.getScreenshotAs(OutputType.BYTES);
    }

    public void goTo(String url){
        wDriver.get(url);
    }


    public void startDriver() {
        if(properties.getDriver().getName().equals("chrome")){
            System.setProperty(properties.getDriver().getChromeDriverName(), properties.getDriver().getChromePath());
            wDriver = new ChromeDriver();
        }else{
            FirefoxOptions firefoxOptions = new FirefoxOptions().setProfile(new FirefoxProfile());
            wDriver = new FirefoxDriver(firefoxOptions);
        }
        wDriver.manage().window().maximize();
        wDriver.manage().timeouts().implicitlyWait(properties.getTimeout().getLongTimeout(), TimeUnit.SECONDS);
        JSWaiter.setDriver(wDriver);
    }

    public void stopDriver() {
        wDriver.quit();
    }
}
