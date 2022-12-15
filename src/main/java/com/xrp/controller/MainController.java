package com.xrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xrpl.xrpl4j.wallet.Wallet;

@RestController
@RequestMapping("/api/v2/xrp")
public class MainController {

    @Autowired
    @Qualifier("xrpController")
    public XRPController xrpController;

    @Autowired
    @Qualifier("walletController")
    public XRPWalletController xrpWalletController;

    @GetMapping(value = "/generate", produces = "application/json")
    public Wallet newWallet() {
        System.out.println("generate");
        return xrpWalletController.generateWallet();
    }
}
