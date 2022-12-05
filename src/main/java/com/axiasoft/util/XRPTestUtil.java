package com.axiasoft.util;

import com.axiasoft.config.AppConfig;
import com.axiasoft.controller.XRPController;
import com.axiasoft.controller.XRPWalletController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.beans.Beans;

public class XRPTestUtil {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        XRPController xrpController = ac.getBean("xrpController",XRPController.class);
        XRPWalletController xrpWalletController = ac.getBean("walletController",XRPWalletController.class);
        String pubKey = "EDCB72D7DFFDDEA267F256A9FFAD3D356C66201E9BF14BBEBD7E7C8691396AEADA";
        String priKey = "EDDEC5B09C3C0CBD2E80CAC17C7C850E35B094E3D3DF31E03E23F89E0ECA5A4F76";
        Wallet test = xrpWalletController.getTestWallet(pubKey, priKey);
        xrpWalletController.walletInfo(test);
        xrpController.checkBalance(test.classicAddress());
        xrpController.checkServer();
    }
}
