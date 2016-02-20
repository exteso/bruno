package com.exteso.bruno.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@EnableAutoConfiguration(exclude = { FlywayAutoConfiguration.class })
public class SpringBootInitializer {

    
}
