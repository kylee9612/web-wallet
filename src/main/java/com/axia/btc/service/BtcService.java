package com.axia.btc.service;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BtcService {
    @Value("${coin.test}")
    private boolean isTest;

    private NetworkParameters network;

    @PostConstruct
    public void getTestnetParam(){
        if(isTest)
            network = MainNetParams.fromID(MainNetParams.ID_TESTNET);
        else
            network = MainNetParams.fromID(MainNetParams.ID_MAINNET);
    }

    public Wallet generateWallet(){
        return Wallet.createDeterministic(network, Script.ScriptType.P2WPKH);
    }

}
