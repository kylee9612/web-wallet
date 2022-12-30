package com.xrp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xrp.model.vo.User;
import com.xrp.model.vo.XrpAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.fees.FeeResult;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@Transactional
@RequestMapping(value = "api/v2/xrp")
public class MainController {
    private static final Logger log = LogManager.getLogger(MainController.class);

    @Autowired
    private XrpController xrpController;
    @Autowired
    private XrpWalletController xrpWalletController;

    @Autowired
    private UserController userController;

    @GetMapping("/generate")
    public JSONObject newWallet() {
        log.info("New Wallet");
        Wallet wallet = xrpWalletController.generateWallet();
        return xrpWalletController.getWalletInfo(wallet);
    }
    @GetMapping("/newUser")
    public JSONObject newUser(){
        log.info("New User");
        User user = userController.generateUser();
        XrpAccount xrpAccount = xrpController.generateAccount(user);
        JSONObject object = new JSONObject();
        object.put("mb_idx",xrpAccount.getMb_idx());
        object.put("address",xrpAccount.getAddress());
        object.put("destination tag",xrpAccount.getDestination());
        object.put("balance",xrpAccount.getBalance());
        return object;
    }

    @PostMapping("/wallet")
    public JSONObject getWallet(@RequestBody HashMap<String, Object> request) throws JsonRpcClientErrorException {
        String publicKey = request.get("publicKey").toString();
        String privateKey = request.get("privateKey").toString();
        String tag = request.get("tag").toString();
        Wallet wallet = xrpWalletController.getWallet(publicKey, privateKey);
        JSONObject object = xrpWalletController.getWalletInfoWithBalance(wallet, tag);
        FeeResult fee = xrpController.getFee();
        object.put("fee", fee.drops().baseFee());
        log.info(object);
        return object;
    }


    @GetMapping("/serverInfo")
    public String serverInfo() {
        return xrpController.getInfo().toString();
    }

    @GetMapping("/checkServer")
    public String checkServer() {
        return xrpController.checkServer();
    }

    @GetMapping("/fundFaucet")
    public String fundFaucet(@RequestParam("address") String address, @RequestParam("tag") String tag) throws Exception {
        Address address1 = Address.builder().value(address).build();
        xrpController.fundFaucet(address1, tag);
        return xrpController.checkBalance(address1, tag);
    }

    @GetMapping("/balance")
    public String getBalance(@RequestParam("address") String address, @RequestParam("tag") String tag) throws JsonRpcClientErrorException {
        log.info("address : " + address + " checked balance");
        return xrpController.checkBalance(Address.of(address), tag);
    }

    @PostMapping("/send")
    public JSONObject send(@RequestBody HashMap<String, Object> map) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        String publicKey = map.get("publicKey").toString();
        String privateKey = map.get("privateKey").toString();
        String toAddress = map.get("address").toString();
        String toTag = map.get("to_tag").toString();
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(map.get("amount").toString()));
        Wallet wallet = xrpWalletController.getWallet(publicKey, privateKey);
        xrpController.sendXRP(wallet, toAddress, toTag, amount);
        JSONObject object = xrpWalletController.getWalletInfo(wallet);
        object.put("to_balance", xrpController.checkBalance(Address.of(toAddress), toTag));
        log.info("address : " + wallet.classicAddress() + " sending " + amount + "XRP to " + toAddress + ":" + toTag);
        return object;
    }

    @GetMapping("/accountInfo")
    public JSONObject accountInfo(@RequestParam String address) throws JsonRpcClientErrorException {
        AccountInfoResult result = xrpController.getAccountInfo(xrpController.getAccountInfoRequest(Address.of(address)));
        JSONObject object = new JSONObject();
        object.put("Transaction ID", result.accountData().accountTransactionId());
        object.put("Account", result.accountData().account());
        object.put("Regular Key", result.accountData().regularKey());
        object.put("Ledger Index", result.ledgerIndex().get());
        log.info(object);
        return object;
    }
}
