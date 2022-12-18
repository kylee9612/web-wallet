package com.xrp.util;

import com.xrp.controller.XRPController;
import com.xrp.controller.XRPWalletController;
import org.xrpl.xrpl4j.wallet.Wallet;

public class XRPWalletGenerator{
    public static void main(String[] args) {
//        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//        XRPWalletController xrpWalletController = ac.getBean("walletController",XRPWalletController.class);
        XRPWalletController xrpWalletController = new XRPWalletController();
        XRPController xrpController = new XRPController();
        Wallet testWallet = xrpWalletController.generateWallet();
        xrpWalletController.walletInfo(testWallet);
        xrpController.fundFaucet(testWallet.classicAddress());
        xrpController.checkBalance(testWallet.classicAddress());
    }
}
