package com.cc.test.framework;

import org.openqa.selenium.remote.RemoteWebElement;

public class CcWebElement extends RemoteWebElement {

    private final String id;
    private final String cssClass;

    public CcWebElement() {
        super();
        this.id = super.getAttribute("id");
        this.cssClass = super.getAttribute("class");
    }

    public String getElementId() {
        return id;
    }

    public String getCssClass() {
        return cssClass;
    }

}
