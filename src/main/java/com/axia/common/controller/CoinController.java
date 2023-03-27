package com.axia.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public abstract class CoinController {

    @Value("${coin.test}")
    protected boolean isTest;

    public abstract Object newWallet();
    public abstract Map<String, Object> getWallet(String privateKey);
    public abstract String deriveAddress(String publicKey);
    public abstract String derivePublicKey(String privateKey);
    public abstract Map<String, Object> sendToAddress(String privateKey, String toAddress, BigDecimal amount);
    public abstract Map<String, Object> checkBalance(String address);
}
