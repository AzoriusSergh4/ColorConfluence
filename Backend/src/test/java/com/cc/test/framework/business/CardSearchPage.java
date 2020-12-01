package com.cc.test.framework.business;
import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

@Service
@ScenarioScope
public class CardSearchPage extends BasePage{

    public CardSearchPage() {
    }

    public boolean checkSiteIsLoaded(){
        return seleniumService.isWebElementBy(By.xpath("//div[@class='grid-container mtgCard-results']"));
    }

    public boolean checkCardsAreLoaded(){
        return seleniumService.findWebElementsBy(By.xpath("//div[contains(@class,'mtgCard-result-item')]//img")).size() > 0;
    }
}
