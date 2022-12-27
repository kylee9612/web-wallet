package com.xrp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PropConfig {
    @Bean("prop")
    Properties properties(){
        return System.getProperties();
    }
}
