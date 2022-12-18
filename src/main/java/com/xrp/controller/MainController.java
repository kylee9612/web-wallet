package com.xrp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.wallet.Wallet;
import org.xrpl.xrpl4j.wallet.WalletFactory;

@RestController
@RequestMapping(value = "api/v2/xrp")
public class MainController {

    @Autowired
    private XRPController xrpController;

    @Autowired
    private XRPWalletController xrpWalletController;

    @GetMapping("/generate")
    public String newWallet() {
        System.out.println("generate");
        Wallet wallet = xrpWalletController.generateWallet();
        System.out.println(xrpController.accountTransactionsResult(wallet.classicAddress()));
        return wallet.toString();
    }

    @GetMapping("/wallet")
    public String getWallet(@RequestParam("address") String address) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        Address address1 = Address.builder().value(address).build();
        System.out.println(address1);
        String pubKey = "EDCB72D7DFFDDEA267F256A9FFAD3D356C66201E9BF14BBEBD7E7C8691396AEADA";
        String priKey = "EDDEC5B09C3C0CBD2E80CAC17C7C850E35B094E3D3DF31E03E23F89E0ECA5A4F76";
        Wallet wallet = xrpWalletController.getWallet(pubKey, priKey);
        xrpController.sendXRP(wallet, address);
        return xrpController.checkBalance(address1);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/serverInfo")
    public String serverInfo() throws JsonRpcClientErrorException {
        return xrpController.getInfo().toString();
    }

    @GetMapping("/fundFaucet")
    public String fundFaucet(@RequestParam("address") String address) {
        Address address1 = Address.builder().value(address).build();
        xrpController.fundFaucet(address1);
        return xrpController.checkBalance(address1);
    }

    @GetMapping("/balance")
    public String getBalance(@RequestParam("address") String address) {
        Address address1 = Address.builder().value(address).build();
        return xrpController.checkBalance(address1);
    }
}
