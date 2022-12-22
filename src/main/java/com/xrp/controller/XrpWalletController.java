package com.xrp.controller;

import com.xrp.service.SignService;
import com.xrp.service.XrpRPCService;
import com.xrp.service.XrpWalletService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
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

    @Value("${xrp.test}")
    private boolean isTest;


    public Wallet generateWallet() {
        Wallet wallet = xrpWalletService.generateWallet();
        if(isTest) {
            xrpController.fundFaucet(wallet.classicAddress());
        }
        return wallet;
    }

    public Wallet getWallet(String secretKey) {
        return xrpWalletService.getWallet(secretKey);
    }

    public Wallet getWallet(String publicKey, String privateKey) {
        return xrpWalletService.getWallet(publicKey,privateKey);
    }

    public JSONObject getWalletInfoWithBalance(Wallet wallet) throws JsonRpcClientErrorException {
        JSONObject object = getWalletInfo(wallet);
        String balance = xrpController.checkBalance(wallet.classicAddress());
        object.put("balance",balance);
        return object;
    }

    public JSONObject getWalletInfo(Wallet wallet){
        JSONObject object = new JSONObject();
        object.put("publicKey",wallet.publicKey().toString());
        object.put("privateKey",wallet.privateKey().get().toString());
        object.put("classicAddress",wallet.classicAddress().toString());
        object.put("xAddress",wallet.xAddress().toString());
        return object;
    }
}
