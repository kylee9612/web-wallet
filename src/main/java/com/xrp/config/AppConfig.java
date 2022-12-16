package com.xrp.config;

import com.xrp.controller.MainController;
import com.xrp.controller.XRPController;
import com.xrp.controller.XRPWalletController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
