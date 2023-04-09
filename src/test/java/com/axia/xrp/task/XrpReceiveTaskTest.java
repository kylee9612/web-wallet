package com.axia.xrp.task;

import com.axia.dao.master.XrpAccountRepo;
import com.axia.dao.master.XrpWalletRepo;
import com.axia.model.vo.XrpAccount;
import com.axia.model.vo.XrpWallet;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xrpl.xrpl4j.model.transactions.Payment;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.math.BigDecimal;

public class XrpReceiveTaskTest extends Thread{

    private final static Logger log = LogManager.getLogger(XrpReceiveTaskTest.class);

    private Payment payment;

    private final String walletAddress;
    private final XrpWalletRepo xrpWalletRepo;
    private final XrpAccountRepo xrpAccountRepo;


    public XrpReceiveTaskTest(Payment payment, String walletAddress, XrpWalletRepo xrpWalletRepo, XrpAccountRepo xrpAccountRepo) {
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
