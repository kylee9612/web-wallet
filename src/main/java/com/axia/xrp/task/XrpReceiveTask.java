package com.axia.xrp.task;

import com.axia.common.task.AbstractDemonTask;
import com.axia.dao.master.XrpAccountRepo;
import com.axia.dao.master.XrpWalletRepo;
import com.axia.model.vo.XrpAccount;
import com.axia.model.vo.XrpWallet;
import com.axia.xrp.service.XrpClientService;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import okhttp3.HttpUrl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.ledger.LedgerResult;
import org.xrpl.xrpl4j.model.client.transactions.TransactionRequestParams;
import org.xrpl.xrpl4j.model.client.transactions.TransactionResult;
import org.xrpl.xrpl4j.model.ledger.LedgerHeader;
import org.xrpl.xrpl4j.model.transactions.*;
import org.xrpl.xrpl4j.wallet.Wallet;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

public class XrpReceiveTask extends Thread{

    private final static Logger log = LogManager.getLogger(XrpReceiveTask.class);

    private Payment payment;

    private final String walletAddress;
    private final XrpWalletRepo xrpWalletRepo;
    private final XrpAccountRepo xrpAccountRepo;


    public XrpReceiveTask(Payment payment, String walletAddress, XrpWalletRepo xrpWalletRepo, XrpAccountRepo xrpAccountRepo) {
        this.payment = payment;
        this.walletAddress = walletAddress;
        this.xrpWalletRepo = xrpWalletRepo;
        this.xrpAccountRepo = xrpAccountRepo;
    }

    @Override
    public void run(){
        log.info("Receive Task Start");
        if(payment.destinationTag().isPresent()){
            UnsignedInteger tag = payment.destinationTag().get();
            XrpAccount xrpAccount = xrpAccountRepo.findByDestination(tag.intValue()).get();
            BigDecimal amount = XrpCurrencyAmount.of(UnsignedLong.valueOf((payment.amount().toString()))).toXrp();
            xrpAccount.setBalance(xrpAccount.getBalance().add(amount));
            xrpAccountRepo.save(xrpAccount);
        }else{
            XrpWallet xrpWallet = xrpWalletRepo.findById(walletAddress).get();
            BigDecimal amount = XrpCurrencyAmount.of(UnsignedLong.valueOf((payment.amount().toString()))).toXrp();
            xrpWallet.setBalance(xrpWallet.getBalance().add(amount));
            xrpWalletRepo.save(xrpWallet);
        }
    }

}
