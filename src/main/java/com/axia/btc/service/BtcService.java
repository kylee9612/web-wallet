package com.axia.btc.service;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BtcService {
    @Value("${xrp.test}")
    private boolean isTest;

    @PostConstruct
    public NetworkParameters getTestnetParam(){
        if(isTest)
            return MainNetParams.fromID(MainNetParams.ID_TESTNET);
        else
            return MainNetParams.fromID(MainNetParams.ID_MAINNET);
    }


}
