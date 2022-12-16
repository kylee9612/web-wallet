package com.xrp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xrpl.xrpl4j.wallet.Wallet;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v2/xrp")
public class MainController {

    private final XRPController xrpController;

    private final XRPWalletController xrpWalletController;

    @GetMapping("/generate")
    public String newWallet() {
        System.out.println("generate");
        return xrpWalletController.generateWallet().toString();
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
