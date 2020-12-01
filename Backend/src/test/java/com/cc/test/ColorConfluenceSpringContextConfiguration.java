package com.cc.test;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.cc.ColorConfluenceApplication;

/**
 * Class to use spring application context while running cucumber
 */

@CucumberContextConfiguration
@SpringBootTest(classes = {ColorConfluenceTestConfig.class})
@ContextConfiguration(classes = ColorConfluenceApplication.class, loader = SpringBootContextLoader.class)
public class ColorConfluenceSpringContextConfiguration {
    @Before
    public void setup_cucumber_spring_context() {
        // Dummy method so cucumber will recognize this class as glue
        // and use its context configuration.
    }
}
