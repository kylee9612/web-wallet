package com.xrp.service;

import com.xrp.controller.XrpController;
import com.xrp.dao.master.XrpAccountRepo;
import com.xrp.dao.slave.XrpAccountSlaveRepo;
import com.xrp.model.vo.XrpAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.model.transactions.Address;

import java.math.BigDecimal;

@Service
public class XrpAccountService {
    private static Logger log = LoggerFactory.getLogger(XrpAccount.class);
    @Autowired
    private XrpAccountRepo xrpAccountRepo;
    @Autowired
    private XrpAccountSlaveRepo xrpAccountSlaveRepo;

    @Autowired
    private XrpClientService xrpClientService;

    @Value("${xrp.test}")
    private boolean isTest;

    public XrpAccount getXrpAccount(long mbIdx){
        return xrpAccountSlaveRepo.findById(mbIdx).orElse(null);
    }

    public XrpAccount generateAccount(long mbIdx) throws Exception {
        int tag = (int) (Math.random()*1000000);
        while(xrpAccountSlaveRepo.findByDestination(tag).isPresent()){
            tag = (int) (Math.random()*1000000);
        }
        BigDecimal balance = isTest ? BigDecimal.valueOf(1000) : BigDecimal.ZERO;
        if(isTest)
            xrpClientService.fundFaucet(Address.of(""));
        XrpAccount account = new XrpAccount(mbIdx,"",tag, balance);
        log.info(account+"");
        return account;
    }
}
