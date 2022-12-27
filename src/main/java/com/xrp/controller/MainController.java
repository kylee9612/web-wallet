package com.xrp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.fees.FeeResult;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping(value = "api/v2/xrp")
public class MainController {

    @Autowired
    private XrpController xrpController;

    @Autowired
    private XrpWalletController xrpWalletController;

    @GetMapping("/generate")
    public JSONObject newWallet(){
        System.out.println("generate");
        Wallet wallet = xrpWalletController.generateWallet();
        return xrpWalletController.getWalletInfo(wallet);
    }

    @PostMapping("/wallet")
    public JSONObject getWallet(@RequestBody HashMap<String,Object> request) throws JsonRpcClientErrorException {
        String publicKey = request.get("publicKey").toString();
        String privateKey = request.get("privateKey").toString();
        Wallet wallet = xrpWalletController.getWallet(publicKey,privateKey);
        JSONObject object = xrpWalletController.getWalletInfoWithBalance(wallet);
        FeeResult fee = xrpController.getFee();
        object.put("fee",fee.drops().baseFee());
        System.out.println(object);
        return object;
    }

    @GetMapping("/serverInfo")
    public String serverInfo(){
        return xrpController.getInfo().toString();
    }

    @GetMapping("/checkServer")
    public String checkServer(){
        return xrpController.checkServer();
    }

    @GetMapping("/fundFaucet")
    public String fundFaucet(@RequestParam("address") String address) throws JsonRpcClientErrorException {
        Address address1 = Address.builder().value(address).build();
        xrpController.fundFaucet(address1);
        return xrpController.checkBalance(address1);
    }

    @GetMapping("/balance")
    public String getBalance(@RequestParam("address") String address) throws JsonRpcClientErrorException {
        return xrpController.checkBalance(Address.of(address));
    }

    @GetMapping("/send")
    public String send(@RequestParam("address")String address) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        Wallet wallet = xrpWalletController.generateWallet();
        xrpController.fundFaucet(wallet.classicAddress());
        xrpController.sendXRP(wallet,address, BigDecimal.valueOf(10));
        return xrpController.checkBalance(wallet.classicAddress());
    }

    @PostMapping("/send")
    public JSONObject send(@RequestBody HashMap<String,Object> map) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        String publicKey = map.get("publicKey").toString();
        String privateKey = map.get("privateKey").toString();
        String toAddress = map.get("address").toString();
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(map.get("amount").toString()));
        Wallet wallet = xrpWalletController.getWallet(publicKey,privateKey);
        xrpController.sendXRP(wallet,toAddress,amount);
        JSONObject object = xrpWalletController.getWalletInfo(wallet);
        object.put("to_balance",xrpController.checkBalance(Address.of(toAddress)));
        return object;
    }

    @GetMapping("/accountInfo")
    public JSONObject accountInfo(@RequestParam String address) throws JsonRpcClientErrorException {
        AccountInfoResult result = xrpController.getAccountInfo(xrpController.getAccountInfoRequest(Address.of(address)));
        JSONObject object = new JSONObject();
        object.put("Transaction ID",result.accountData().accountTransactionId());
        object.put("Account",result.accountData().account());
        object.put("Regular Key",result.accountData().regularKey());
        object.put("Ledger Index",result.ledgerIndex().get());
        return object;
    }
}
