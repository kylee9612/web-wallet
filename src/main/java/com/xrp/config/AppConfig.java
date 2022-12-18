package com.xrp.config;

import com.google.j2objc.annotations.Property;
import com.xrp.controller.MainController;
import com.xrp.controller.XRPController;
import com.xrp.controller.XRPWalletController;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Primary
    @Bean(name = "xrpController")
    public XRPController xrpController() {
        return new XRPController();
    }

    @Primary
    @Bean(name = "walletController")
    public XRPWalletController walletController() {
        return new XRPWalletController();
    }
}
