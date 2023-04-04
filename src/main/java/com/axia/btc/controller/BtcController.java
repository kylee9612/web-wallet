package com.axia.btc.controller;

import com.axia.btc.service.BtcService;
import com.axia.btc.service.BtcWalletService;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class BtcController {
    @Autowired
    private BtcService btcService;
    @Autowired
    private BtcWalletService btcWalletService;

    public Wallet generateWallet(){
        Wallet wallet = null;
        try {
            wallet = btcWalletService.generateWallet(btcService.getTestnetParam());
            Map<String, String> map = btcWalletService.processWallet(wallet);
            System.out.println("Address : \t" + map.get("Address"));
            System.out.println("Balance : \t" + map.get("Balance"));
            System.out.println("Seed : \t" + map.get("Seed"));
            System.out.println("CreationTime : \t" + map.get("CreationTime"));
            System.out.println("Mnemonics : \t" + map.get("Mnemonics"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return wallet;
    }
}
