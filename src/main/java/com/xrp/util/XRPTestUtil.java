package com.xrp.util;

import com.xrp.controller.XRPController;
import com.xrp.controller.XRPWalletController;
import org.xrpl.xrpl4j.wallet.Wallet;

public class XRPTestUtil {
    public static void main(String[] args) {
        XRPController xrpController = new XRPController();
        XRPWalletController xrpWalletController = new XRPWalletController();
        String pubKey = "EDCB72D7DFFDDEA267F256A9FFAD3D356C66201E9BF14BBEBD7E7C8691396AEADA";
        String priKey = "EDDEC5B09C3C0CBD2E80CAC17C7C850E35B094E3D3DF31E03E23F89E0ECA5A4F76";
        Wallet test = xrpWalletController.getWallet(pubKey, priKey);
        xrpWalletController.walletInfo(test);
        xrpController.checkBalance(test.classicAddress());
        xrpController.checkServer();
    }
}
