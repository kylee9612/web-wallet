package com.xrp.controller;

import org.springframework.stereotype.Controller;
import org.xrpl.xrpl4j.keypairs.KeyPair;
import org.xrpl.xrpl4j.wallet.DefaultWalletFactory;
import org.xrpl.xrpl4j.wallet.Wallet;
import org.xrpl.xrpl4j.wallet.WalletFactory;

@Controller
public class XRPWalletController {

    private final WalletFactory walletFactory = DefaultWalletFactory.getInstance();

    public Wallet generateTestWallet() {
        return walletFactory.randomWallet(true).wallet();
    }
    public Wallet generateWallet(){
        return walletFactory.randomWallet(false).wallet();
    }
    public KeyPair getKeyPair(String publicKey, String privateKey){
        return KeyPair.builder().
                publicKey(publicKey).
                privateKey(privateKey).
                build();
    }
    public Wallet getTestWallet(String publicKey, String privateKey) {
        KeyPair key = getKeyPair(publicKey,privateKey);
        return walletFactory.fromKeyPair(key, true);
    }
    public Wallet getWallet(String publicKey, String privateKey){
        KeyPair key = getKeyPair(publicKey, privateKey);
        return walletFactory.fromKeyPair(key,false);
    }
    public void walletInfo(Wallet wallet) {
        System.out.println("Public key : " + wallet.publicKey());
        System.out.println("Private key : " + wallet.privateKey().get());
        System.out.println("Classic Address : " + wallet.classicAddress());
        System.out.println("xAddress : " + wallet.xAddress());
    }
}
