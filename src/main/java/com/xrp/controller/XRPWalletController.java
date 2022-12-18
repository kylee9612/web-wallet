package com.xrp.controller;

import com.xrp.service.SignService;
import com.xrp.service.XrpWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.xrpl.xrpl4j.keypairs.KeyPair;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.util.Arrays;

@Controller
public class XRPWalletController {

    private static final Logger log = LoggerFactory.getLogger(XRPWalletController.class);

    @Autowired
    private SignService signService;
    @Autowired
    private XrpWalletService xrpWalletService;
    @Autowired
    private XRPController xrpController;


    public Wallet generateWallet() {
        Wallet wallet = xrpWalletService.generateWallet();
        signService.signUsingSingleKeySignatureService(wallet);
        try {
            log.info(xrpController.getRegularKey(wallet).toString());
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return wallet;
    }

    public KeyPair getKeyPair(String publicKey, String privateKey) {
        return xrpWalletService.getKeyPair(publicKey,privateKey);
    }

    public Wallet getWallet(String secretKey) {
        return xrpWalletService.getWallet(secretKey);
    }

    public Wallet getWallet(String publicKey, String privateKey) {
        return xrpWalletService.getWallet(publicKey,privateKey);
    }

    public void walletInfo(Wallet wallet) {
        xrpWalletService.walletInfo(wallet);
    }
}
