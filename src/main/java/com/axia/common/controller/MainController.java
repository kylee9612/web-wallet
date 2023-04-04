package com.axia.common.controller;

import com.axia.btc.controller.BtcController;
import com.axia.xrp.controller.XrpController;
import com.axia.xrp.controller.XrpWalletController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.axia.model.vo.User;
import com.axia.model.vo.XrpAccount;
import com.axia.btc.util.BtcUtil;
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
@RequestMapping(value = "api/v2")
public class MainController {
    private static final Logger log = LogManager.getLogger(MainController.class);

    @Autowired
    private XrpController xrpController;
    @Autowired
    private XrpWalletController xrpWalletController;

    @Autowired
    private BtcController btcController;

    @Autowired
    private UserController userController;

    @GetMapping("/xrp/generate")
    public JSONObject xrpNewWallet() {
        log.info("XRP New Wallet");
        Wallet wallet = xrpWalletController.generateWallet();
        return xrpWalletController.getWalletInfo(wallet);
    }
    @GetMapping("/btc/generate")
    public JSONObject btcNewWallet(){
        log.info("BTC New Wallet");
        org.bitcoinj.wallet.Wallet wallet = btcController.generateWallet();
        return BtcUtil.getWallet(wallet);
    }

    @GetMapping("/user/generate")
    public JSONObject newUser() {
        log.info("New User");
        User user = userController.generateUser();
        JSONObject object = new JSONObject();
        try {
            XrpAccount xrpAccount = xrpController.generateAccount(user);
            object.put("mb_idx", user.getMb_idx());
            object.put("address", xrpAccount.getAddress());
            object.put("destination", xrpAccount.getDestination());
            object.put("balance", xrpAccount.getBalance());
        }catch (Exception e){
           log.error(e.getMessage());
           object.put("Error","Faucet Error");
        }
        return object;
    }

    @PostMapping("/xrp/wallet")
    public JSONObject getXrpWallet(@RequestBody HashMap<String, Object> request) throws JsonRpcClientErrorException {
        String publicKey = request.get("publicKey").toString();
        String privateKey = request.get("privateKey").toString();
        int tag = 0;
        if(request.containsKey("tag")){
            tag= Integer.parseInt(request.get("tag").toString());
        }
        Wallet wallet = xrpWalletController.getWallet(publicKey, privateKey);
        JSONObject object = xrpWalletController.getWalletInfoWithBalance(wallet, tag);
        FeeResult fee = xrpController.getFee();
        object.put("fee", fee.drops().baseFee());
        log.info(object);
        return object;
    }


    @GetMapping("/xrp/serverInfo")
    public String serverInfo() {
        return xrpController.getInfo().toString();
    }

    @GetMapping("/xrp/checkServer")
    public String checkServer() {
        return xrpController.checkServer();
    }

    @GetMapping("/xrp/fundFaucet")
    public String fundFaucet(@RequestParam("address") String address, @RequestParam("tag") int tag) throws Exception {
        Address address1 = Address.builder().value(address).build();
        xrpController.fundFaucet(address1, tag);
        return xrpController.checkBalance(address1, tag);
    }

    @GetMapping("/xrp/balance")
    public String getBalance(@RequestParam("address") String address, @RequestParam("tag") int tag) throws JsonRpcClientErrorException {
        log.info("address : " + address + " checked balance");
        return xrpController.checkBalance(Address.of(address), tag);
    }

    @PostMapping("/xrp/send")
    public JSONObject send(@RequestBody HashMap<String, Object> map) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        String publicKey = map.get("publicKey").toString();
        String privateKey = map.get("privateKey").toString();
        String toAddress = map.get("address").toString();
        int toTag = Integer.parseInt(map.get("to_tag").toString());
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(map.get("amount").toString()));
        Wallet wallet = xrpWalletController.getWallet(publicKey, privateKey);
        xrpController.sendXRP(wallet, toAddress, toTag, amount);
        JSONObject object = xrpWalletController.getWalletInfo(wallet);
        object.put("to_balance", xrpController.checkBalance(Address.of(toAddress), toTag));
        log.info("address : " + wallet.classicAddress() + " sending " + amount + "XRP to " + toAddress + ":" + toTag);
        return object;
    }

    @GetMapping("/xrp/accountInfo")
    public JSONObject xrpAccountInfo(@RequestParam String address) throws JsonRpcClientErrorException {
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
