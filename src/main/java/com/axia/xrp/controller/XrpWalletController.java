package com.axia.xrp.controller;

import com.axia.dao.master.XrpAccountRepo;
import com.axia.dao.master.XrpWalletRepo;
import com.axia.model.vo.XrpWallet;
import com.axia.xrp.service.SignService;
import com.axia.xrp.service.XrpWalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.codec.addresses.Base58;
import org.xrpl.xrpl4j.codec.addresses.UnsignedByteArray;
import org.xrpl.xrpl4j.crypto.*;
import org.xrpl.xrpl4j.keypairs.DefaultKeyPairService;
import org.xrpl.xrpl4j.keypairs.KeyPair;
import org.xrpl.xrpl4j.keypairs.KeyPairService;
import org.xrpl.xrpl4j.wallet.Wallet;
import org.xrpl.xrpl4j.wallet.WalletFactory;

import java.io.IOException;
import java.util.Map;

@RestController
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


    @GetMapping("/api/v2/xrp/generate")
    public Wallet generateWallet() {
        Wallet wallet = xrpWalletService.generateWallet();
        if(isTest) {
            xrpController.fundFaucet(wallet.classicAddress());
        }
        XrpWallet xrpWallet = new XrpWallet(wallet);
        xrpWalletRepo.save(xrpWallet);
        return wallet;
    }


    @PostMapping("/api/v2/xrp/wallet")
    public Wallet getWallet(@RequestBody Map<String,Object> map){
        String privateK = map.get("private").toString();
        PrivateKey privateKey = PrivateKey.fromBase16EncodedPrivateKey(privateK);
        return xrpWalletService.getWallet(privateKey);
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
