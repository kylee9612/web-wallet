package com.axiasoft.config;

import com.axiasoft.controller.XRPController;
import com.axiasoft.controller.XRPWalletController;
import com.axiasoft.service.XrpClientService;
import com.axiasoft.service.XrpWalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackageClasses = SpringApplication.class)
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

    @Primary
    @Bean(name = "xrpClientService")
    public XrpClientService xrpClientService() {
        return new XrpClientService();
    }

    @Primary
    @Bean(name = "xrpWalletService")
    public XrpWalletService xrpWalletService() {
        return new XrpWalletService();
    }
}
