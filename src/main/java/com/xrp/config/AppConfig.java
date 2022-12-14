package com.xrp.config;

import com.xrp.controller.XRPController;
import com.xrp.controller.XRPWalletController;
import com.xrp.service.XrpClientService;
import com.xrp.service.XrpWalletService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
