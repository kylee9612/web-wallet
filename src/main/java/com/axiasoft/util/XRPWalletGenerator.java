package com.axiasoft.util;

import com.axiasoft.config.AppConfig;
import com.axiasoft.controller.XRPController;
import com.axiasoft.controller.XRPWalletController;
import org.springframework.beans.factory.annotation.Autowired;
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
