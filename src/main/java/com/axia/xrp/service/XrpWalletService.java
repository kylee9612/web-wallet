package com.axia.xrp.service;

import com.axia.dao.master.XrpWalletRepo;
import com.axia.model.vo.XrpWallet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.crypto.bc.BcAddressUtils;
import org.xrpl.xrpl4j.crypto.bc.keys.BcKeyUtils;
import org.xrpl.xrpl4j.crypto.core.keys.PrivateKey;
import org.xrpl.xrpl4j.crypto.core.keys.PublicKey;
import org.xrpl.xrpl4j.keypairs.KeyPair;
import org.xrpl.xrpl4j.wallet.DefaultWalletFactory;
import org.xrpl.xrpl4j.wallet.Wallet;
import org.xrpl.xrpl4j.wallet.WalletFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class XrpWalletService {
    private static final Logger log = LogManager.getLogger(XrpWalletService.class);

    @Value("${xrp.test}")
    private boolean isTest;
    @Autowired
    private XrpWalletRepo xrpWalletRepo;
    private final WalletFactory walletFactory = DefaultWalletFactory.getInstance();

    public Wallet generateWallet() {
        Wallet wallet = walletFactory.randomWallet(isTest).wallet();
        XrpWallet xrpWallet = new XrpWallet(wallet);
        xrpWalletRepo.save(xrpWallet);
        return wallet;
    }

    public Wallet getWallet(PrivateKey privateKey) {
        PublicKey publicKey = BcKeyUtils.toPublicKey(privateKey);
        KeyPair keyPair = getKeyPair(publicKey.base16Value(),privateKey.value().hexValue());
        log.info(keyPair);
        return walletFactory.fromKeyPair(keyPair,isTest);
    }

    public Map<String, Object> walletToMap(Wallet wallet){
        Map<String, Object> map = new HashMap<>();
        map.put("private_key", wallet.privateKey().get());
        map.put("public_key", wallet.publicKey());
        map.put("address",wallet.classicAddress().toString());
        return map;
    }

    public String derivePublicKey(PrivateKey privateKey){
        PublicKey publicKey = BcKeyUtils.toPublicKey(privateKey);
        return publicKey.base16Value();
    }

    public String deriveAddress(org.xrpl.xrpl4j.crypto.core.keys.PublicKey publicKey){
        return BcAddressUtils.getInstance().deriveAddress(publicKey).value();
    }

    private KeyPair getKeyPair(String publicKey, String privateKey) {
        return KeyPair.builder().
                publicKey(publicKey).
                privateKey(privateKey).
                build();
    }
}
