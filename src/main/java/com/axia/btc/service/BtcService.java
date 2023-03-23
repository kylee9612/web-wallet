package com.axia.btc.service;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.KeyChainGroup;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.WalletProtobufSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
public class BtcService {
    @Value("${coin.test}")
    private boolean isTest;

    private NetworkParameters network;
    private WalletProtobufSerializer walletFactory;

    @PostConstruct
    public void getTestnetParam(){
        if(isTest)
            network = MainNetParams.fromID(MainNetParams.ID_TESTNET);
        else
            network = MainNetParams.fromID(MainNetParams.ID_MAINNET);
    }

    public Wallet generateWallet(){
        Wallet wallet = Wallet.createDeterministic(network, Script.ScriptType.P2PKH);
        return wallet;
    }

}
