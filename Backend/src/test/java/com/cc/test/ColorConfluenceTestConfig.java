package com.cc.test;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.cc")
@EnableTransactionManagement
@EnableConfigurationProperties(TestProperties.class)
@ConfigurationPropertiesScan({"com.cc.test"})
public class ColorConfluenceTestConfig {
}
