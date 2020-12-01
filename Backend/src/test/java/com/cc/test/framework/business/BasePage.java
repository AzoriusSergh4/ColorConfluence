package com.cc.test.framework.business;

import com.cc.test.framework.selenium.FrontendTestCommons;
import com.cc.test.framework.selenium.SeleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasePage {

    @Autowired
    SeleniumService seleniumService;
    @Autowired
    FrontendTestCommons testCommons;

    public BasePage(){
    }

    public String getPageTitle() {
        return seleniumService.getPageTitle();
    }
}
