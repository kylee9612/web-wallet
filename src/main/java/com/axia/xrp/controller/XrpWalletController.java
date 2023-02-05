package com.axia.xrp.controller;

import com.axia.dao.master.XrpAccountRepo;
import com.axia.dao.master.XrpWalletRepo;
import com.axia.model.vo.XrpWallet;
import com.axia.xrp.service.SignService;
import com.axia.xrp.service.XrpWalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.wallet.Wallet;

@Controller
public class XrpWalletController {

    private static final Logger log = LogManager.getLogger(XrpWalletController.class);

    @Autowired
    private XrpAccountRepo xrpAccountRepo;

    @Autowired
    private XrpWalletRepo xrpWalletRepo;

    @Autowired
    private SignService signService;
    @Autowired
    private XrpWalletService xrpWalletService;
    @Autowired
    private XrpController xrpController;

    @Value("${xrp.test}")
    private boolean isTest;


    public Wallet generateWallet() {
        Wallet wallet = xrpWalletService.generateWallet();
        if(isTest) {
            xrpController.fundFaucet(wallet.classicAddress());
        }
        XrpWallet xrpWallet = new XrpWallet(wallet);
        xrpWalletRepo.save(xrpWallet);
        return wallet;
    }

    public Wallet getWallet(String secretKey) {
        return xrpWalletService.getWallet(secretKey);
    }

    public Wallet getWallet(String publicKey, String privateKey) {
        return xrpWalletService.getWallet(publicKey,privateKey);
    }

    public JSONObject getWalletInfoWithBalance(Wallet wallet, int tag) throws JsonRpcClientErrorException {
        JSONObject object = getWalletInfo(wallet);
        String balance = xrpController.checkBalance(wallet.classicAddress(), tag);
        object.put("balance",balance);
        return object;
    }

    public JSONObject getWalletInfo(Wallet wallet){
        JSONObject object = new JSONObject();
        object.put("publicKey",wallet.publicKey().toString());
        object.put("privateKey",wallet.privateKey().get().toString());
        object.put("classicAddress",wallet.classicAddress().toString());
        return object;
    }
}
