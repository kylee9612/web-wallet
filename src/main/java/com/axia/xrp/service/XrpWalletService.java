package com.axia.xrp.service;

import com.axia.xrp.util.XrpRequestParamUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.keypairs.KeyPair;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.wallet.DefaultWalletFactory;
import org.xrpl.xrpl4j.wallet.Wallet;
import org.xrpl.xrpl4j.wallet.WalletFactory;

@Service
public class XrpWalletService {
    private static final Logger log = LogManager.getLogger(XrpWalletService.class);

    @Value("${xrp.test}")
    private boolean isTest;
    private final XrpRequestParamUtil requestParamUtil = new XrpRequestParamUtil();
    private final WalletFactory walletFactory = DefaultWalletFactory.getInstance();

    public Wallet generateWallet() {
        return walletFactory.randomWallet(isTest).wallet();
    }

    public KeyPair getKeyPair(String publicKey, String privateKey) {
        return KeyPair.builder().
                publicKey(publicKey).
                privateKey(privateKey).
                build();
    }

    public Wallet getWallet(String publicKey, String privateKey) {
        KeyPair key = getKeyPair(publicKey, privateKey);
        return walletFactory.fromKeyPair(key, isTest);
    }

    public Wallet getWallet(String secretKey) {
        return walletFactory.fromSeed(secretKey, isTest);
    }

    public AccountInfoRequestParams getAccountInfoRequest(Address classicAddress) {
        return requestParamUtil.getAccountInfoRequest(classicAddress);
    }
}
