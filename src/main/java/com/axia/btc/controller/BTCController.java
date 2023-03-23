package com.axia.btc.controller;

import com.axia.btc.service.BtcService;
import com.axia.common.controller.CoinController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/btc")
public class BTCController extends CoinController {

    private static final Logger log = LogManager.getLogger(BTCController.class);

    @Autowired
    private BtcService btcService;
    @Override
    @GetMapping("/newWallet")
    public Object newWallet() {
        Wallet wallet = btcService.generateWallet();
        log.info(wallet.toString(true,true,false,null));
        return wallet;
    }

    @Override
    public Map<String, Object> getWallet(String privateKey) {
        return null;
    }

    @Override
    public String deriveAddress(String publicKey) {
        return null;
    }

    @Override
    public String derivePublicKey(String privateKey) {
        return null;
    }

    @Override
    public Map<String, Object> sendToAddress(String privateKey, String toAddress, BigDecimal amount) {
        return null;
    }

    @Override
    public Map<String, Object> checkBalance(String address) {
        return null;
    }
}
