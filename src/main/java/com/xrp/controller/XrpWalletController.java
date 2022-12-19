package com.xrp.controller;

import com.xrp.service.SignService;
import com.xrp.service.XrpRPCService;
import com.xrp.service.XrpWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.xrpl.xrpl4j.crypto.signing.SignedTransaction;
import org.xrpl.xrpl4j.keypairs.KeyPair;
import org.xrpl.xrpl4j.model.client.XrplMethods;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.transactions.Payment;
import org.xrpl.xrpl4j.model.transactions.SetRegularKey;
import org.xrpl.xrpl4j.wallet.Wallet;

@Controller
public class XrpWalletController {

    private static final Logger log = LoggerFactory.getLogger(XrpWalletController.class);

    @Autowired
    private SignService signService;
    @Autowired
    private XrpWalletService xrpWalletService;
    @Autowired
    private XrpController xrpController;
    @Autowired
    private XrpRPCService xrpRPCService;


    public Wallet generateWallet() {
        Wallet wallet = xrpWalletService.generateWallet();
        System.out.println(wallet);
        SignedTransaction<Payment> signedTransaction = signService.signUsingSingleKeySignatureService(wallet);
        try {
            AccountInfoRequestParams infoRequestParams = xrpController.getAccountInfoRequest(wallet.classicAddress());
            AccountInfoResult result = (AccountInfoResult) xrpRPCService.jsonRpcRequest(XrplMethods.SUBMIT,infoRequestParams);
            SetRegularKey regularKey = xrpController.getRegularKey(wallet);
            log.info("Param : "+infoRequestParams);
            log.info("Result : "+result);
            log.info("RegularKey : "+regularKey);
        } catch (Exception e) {
            log.error(e.getMessage());
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
