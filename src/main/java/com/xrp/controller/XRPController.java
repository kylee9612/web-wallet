package com.xrp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xrp.service.XrpClientService;
import com.xrp.service.XrpWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.accounts.AccountTransactionsResult;
import org.xrpl.xrpl4j.model.transactions.*;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.util.Map;

@Controller
public class XRPController extends Thread {

    private static final Logger log = LoggerFactory.getLogger(XRPController.class);

    @Value("${xrp.test}")
    private boolean isTest;

    @Autowired
    private XrpClientService xrpClientService;
    @Autowired
    private XrpWalletService xrpWalletService;

    public void checkServer() {
        xrpClientService.checkServer();
    }

    public Map<String, Object> getInfo(){
        return xrpClientService.getInfo();
    }

    public void fundFaucet(Address classicAddress) {
        xrpClientService.fundFaucet(classicAddress);
    }

    public AccountTransactionsResult accountTransactionsResult(Address address){
        return xrpClientService.accountTransactionsResult(address);
    }

    public String checkBalance(Address classicAddress) {
        return xrpClientService.checkBalance(classicAddress);
    }

    public SetRegularKey getRegularKey(Wallet wallet){
        return xrpClientService.getRegularKey(wallet);
    }

    public void sendXRP(Wallet testWallet, String addressTo) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        xrpClientService.sendXRP(testWallet,addressTo);
    }

    public AccountInfoResult getAccountInfo(AccountInfoRequestParams resultParams) {
        return xrpClientService.getAccountInfo(resultParams);
    }

    public AccountInfoRequestParams getAccountInfoRequest(Address classicAddress) {
        return xrpWalletService.getAccountInfoRequest(classicAddress);
    }

    private boolean validateWallet(Wallet wallet) {
        return true;
    }
}
