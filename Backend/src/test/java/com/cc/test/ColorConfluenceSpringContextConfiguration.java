package com.cc.test;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;

import com.cc.ColorConfluenceApplication;

/**
 * Class to use spring application context while running cucumber
 */

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = ColorConfluenceApplication.class, loader = SpringBootContextLoader.class)
public class ColorConfluenceSpringContextConfiguration {

}
