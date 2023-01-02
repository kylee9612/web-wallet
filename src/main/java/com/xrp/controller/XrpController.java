package com.xrp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xrp.dao.master.XrpAccountRepo;
import com.xrp.model.vo.User;
import com.xrp.model.vo.XrpAccount;
import com.xrp.service.XrpAccountService;
import com.xrp.service.XrpClientService;
import com.xrp.service.XrpWalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.accounts.AccountTransactionsResult;
import org.xrpl.xrpl4j.model.client.fees.FeeResult;
import org.xrpl.xrpl4j.model.transactions.*;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@Transactional
public class XrpController extends Thread {

    private static final Logger log = LogManager.getLogger(XrpController.class);

    @Autowired
    private XrpAccountRepo xrpAccountRepo;
    @Autowired
    private XrpClientService xrpClientService;
    @Autowired
    private XrpWalletService xrpWalletService;
    @Autowired
    private XrpAccountService xrpAccountService;

    public String checkServer() {
        return xrpClientService.checkServer();
    }

    public Map<String, Object> getInfo() {
        return xrpClientService.getInfo();
    }

    public void fundFaucet(Address classicAddress) {
        xrpClientService.fundFaucet(classicAddress);
    }

    public void fundFaucet(Address classicAddress, int tag){
        String address = classicAddress.toString();
        xrpClientService.fundFaucet(classicAddress);
        XrpAccount account = xrpAccountRepo
                .findFirstByAddressAndDestination(address, tag)
                .orElseThrow();
        BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(xrpClientService.checkBalance(classicAddress, tag)) + 1000);
        account.setBalance(balance);
        xrpAccountRepo.save(account);
    }

    public XrpAccount generateAccount(User user) throws Exception {
        return xrpAccountService.generateAccount(user.getMb_idx());
    }

    public AccountTransactionsResult accountTransactionsResult(Address address) {
        return xrpClientService.accountTransactionsResult(address);
    }

    public String checkBalance(Address classicAddress, int tag) throws JsonRpcClientErrorException {
        return xrpClientService.checkBalance(classicAddress, tag);
    }

    public SetRegularKey getRegularKey(Wallet wallet) {
        return xrpClientService.getRegularKey(wallet);
    }

    public void sendXRP(Wallet testWallet, String addressTo, int tag, BigDecimal amount) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        xrpClientService.sendXRP(testWallet, addressTo, tag, amount);
    }

    public FeeResult getFee() throws JsonRpcClientErrorException {
        return xrpClientService.getFee();
    }

    public AccountInfoResult getAccountInfo(AccountInfoRequestParams resultParams) throws JsonRpcClientErrorException {
        return xrpClientService.getAccountInfo(resultParams);
    }

    public AccountInfoRequestParams getAccountInfoRequest(Address classicAddress) {
        return xrpWalletService.getAccountInfoRequest(classicAddress);
    }

}
