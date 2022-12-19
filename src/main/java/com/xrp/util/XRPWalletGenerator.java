package com.xrp.util;

import com.xrp.controller.XrpController;
import com.xrp.controller.XrpWalletController;
import org.xrpl.xrpl4j.wallet.Wallet;

public class XRPWalletGenerator{
    public static void main(String[] args) {
//        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//        XRPWalletController xrpWalletController = ac.getBean("walletController",XRPWalletController.class);
        XrpWalletController xrpWalletController = new XrpWalletController();
        XrpController xrpController = new XrpController();
        Wallet testWallet = xrpWalletController.generateWallet();
        xrpWalletController.walletInfo(testWallet);
        xrpController.fundFaucet(testWallet.classicAddress());
        xrpController.checkBalance(testWallet.classicAddress());
    }
}
