package com.xrp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import com.xrp.service.XrpClientService;
import com.xrp.service.XrpWalletService;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.xrpl.xrpl4j.client.JsonRpcClient;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.JsonRpcRequest;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.client.faucet.FaucetClient;
import org.xrpl.xrpl4j.client.faucet.FundAccountRequest;
import org.xrpl.xrpl4j.crypto.KeyMetadata;
import org.xrpl.xrpl4j.crypto.PrivateKey;
import org.xrpl.xrpl4j.crypto.signing.SignatureService;
import org.xrpl.xrpl4j.crypto.signing.SignedTransaction;
import org.xrpl.xrpl4j.crypto.signing.SingleKeySignatureService;
import org.xrpl.xrpl4j.model.client.XrplRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.accounts.AccountTransactionsResult;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.fees.FeeResult;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.transactions.SubmitResult;
import org.xrpl.xrpl4j.model.client.transactions.TransactionRequestParams;
import org.xrpl.xrpl4j.model.client.transactions.TransactionResult;
import org.xrpl.xrpl4j.model.immutables.FluentCompareTo;
import org.xrpl.xrpl4j.model.transactions.*;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    public AccountInfoResult getAccountInfo(AccountInfoRequestParams resultParams) {
        return xrpClientService.getAccountInfo(resultParams);
    }

    public AccountInfoRequestParams getAccountInfoRequest(Address classicAddress) {
        return xrpWalletService.getAccountInfoRequest(classicAddress);
    }

    public SetRegularKey getRegularKey(Wallet wallet){
        return xrpClientService.getRegularKey(wallet);
    }

    public void sendXRP(Wallet testWallet, String addressTo) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        xrpClientService.sendXRP(testWallet,addressTo);
    }

    private boolean validateWallet(Wallet wallet) {
        return true;
    }
}
