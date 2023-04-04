package com.axia.xrp.service;

import com.axia.xrp.util.XrpRequestParamUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.crypto.BcKeyUtils;
import org.xrpl.xrpl4j.crypto.PrivateKey;
import org.xrpl.xrpl4j.crypto.PublicKey;
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

    public Wallet getWallet(PrivateKey privateKey) {
        PublicKey publicKey = BcKeyUtils.toPublicKey(privateKey);
        KeyPair keyPair = getKeyPair(publicKey.base16Encoded(),privateKey.base16Encoded());
        log.info(keyPair);
        return walletFactory.fromKeyPair(keyPair,isTest);
    }

    public AccountInfoRequestParams getAccountInfoRequest(Address classicAddress) {
        return requestParamUtil.getAccountInfoRequest(classicAddress);
    }
}
