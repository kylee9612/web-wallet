package com.xrp.util;

import com.xrp.config.AppConfig;
import com.xrp.controller.XRPController;
import com.xrp.controller.XRPWalletController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xrpl.xrpl4j.wallet.Wallet;

public class XRPWalletGenerator{
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        XRPWalletController xrpWalletController = ac.getBean("walletController",XRPWalletController.class);
        XRPController xrpController = new XRPController();
        Wallet testWallet = xrpWalletController.generateTestWallet();
        xrpWalletController.walletInfo(testWallet);
        xrpController.fundFaucet(testWallet.classicAddress());
        xrpController.checkBalance(testWallet.classicAddress());
        XRPTestUtil.main(args);
    }
}
