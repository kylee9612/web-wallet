package com.xrp.controller;

import com.google.common.primitives.UnsignedInteger;
import com.xrp.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.xrpl.xrpl4j.crypto.KeyMetadata;
import org.xrpl.xrpl4j.crypto.PrivateKey;
import org.xrpl.xrpl4j.crypto.signing.SignedTransaction;
import org.xrpl.xrpl4j.crypto.signing.SingleKeySignatureService;
import org.xrpl.xrpl4j.keypairs.DefaultKeyPairService;
import org.xrpl.xrpl4j.keypairs.KeyPair;
import org.xrpl.xrpl4j.keypairs.KeyPairService;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Payment;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;
import org.xrpl.xrpl4j.wallet.DefaultWalletFactory;
import org.xrpl.xrpl4j.wallet.Wallet;
import org.xrpl.xrpl4j.wallet.WalletFactory;

@Controller
public class XRPWalletController {

    @Value("${xrp.test}")
    private boolean isTest;
    private final WalletFactory walletFactory = DefaultWalletFactory.getInstance();

    private final KeyPairService keyPairService = DefaultKeyPairService.getInstance();

    @Autowired
    private SignService signService;

    public Wallet generateWallet(){
        Wallet wallet = walletFactory.randomWallet(isTest).wallet();
        signService.signUsingSingleKeySignatureService(wallet);
        return wallet;
    }
    public KeyPair getKeyPair(String publicKey, String privateKey){
        return KeyPair.builder().
                publicKey(publicKey).
                privateKey(privateKey).
                build();
    }

    public Wallet getWallet(String secretKey){
        return walletFactory.fromSeed(secretKey ,isTest);
    }
    public Wallet getWallet(String publicKey, String privateKey) {
        KeyPair key = getKeyPair(publicKey,privateKey);
        return walletFactory.fromKeyPair(key, isTest);
    }
    public void walletInfo(Wallet wallet) {
        System.out.println("Public key : " + wallet.publicKey());
        System.out.println("Private key : " + wallet.privateKey().get());
        System.out.println("Classic Address : " + wallet.classicAddress());
        System.out.println("xAddress : " + wallet.xAddress());
    }
}
