package com.cc.test.framework;

import io.cucumber.java.Scenario;

public abstract class TestCommons {

    public Scenario scenario;

    abstract void beforeScenario(Scenario scenario);
    abstract void afterScenario(Scenario scenario);

}
