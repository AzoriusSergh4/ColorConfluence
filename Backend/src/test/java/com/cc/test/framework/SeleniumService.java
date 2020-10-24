package com.cc.test.framework;

import com.cc.test.TestProperties;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Component
@ScenarioScope
public class SeleniumService {

    private WebDriver wDriver;

    private final TestProperties properties;

    private final ReportManager reportManager;

    @Autowired
    public SeleniumService(TestProperties properties, ReportManager reportManager){
        this.properties = properties;
        this.reportManager = reportManager;
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


    public CcWebElement findWebElementBy(By by){
        try {
            return (CcWebElement) wDriver.findElement(by);
        } catch (NoSuchElementException n){
            n.printStackTrace();
            throw n;
        }
    }

    public CcWebElement findWebElementById(String id){
        try {
            return (CcWebElement) wDriver.findElement(By.id(id));
        } catch (NoSuchElementException n){
            n.printStackTrace();
            throw n;
        }
    }

    public List<CcWebElement> findWebElementsBy(By by){
        try {
            List<WebElement> elements =  wDriver.findElements(by);
            List<CcWebElement> ccElements = new ArrayList<>();
            for(WebElement e : elements){
                ccElements.add((CcWebElement)e);
            }
            return ccElements;
        } catch (NoSuchElementException n){
            n.printStackTrace();
            throw n;
        }
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
    }

    public void stopDriver() {
        wDriver.quit();
    }
}
