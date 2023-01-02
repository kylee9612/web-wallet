package com.xrp.service;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BtcWalletService {

    public Wallet generateWallet(NetworkParameters params){
        Wallet wallet = Wallet.createBasic(params);
        return Wallet.createBasic(params);
    }

    public Map< String, String> processWallet(Wallet wallet) {
        HashMap< String, String> map = new HashMap<>();
        DeterministicSeed seed = wallet.getActiveKeyChain().getSeed();
        seed.setCreationTimeSeconds(System.currentTimeMillis());
        String creationTime = Utils.dateTimeFormat(seed.getCreationTimeSeconds());
        String seedHex = seed.toHexString();
        List<String> mnemonics = seed.getMnemonicCode();
        String address = wallet.currentReceiveAddress().toString();
        String balance = wallet.getBalance().getValue()+" BTC";
        map.put("Address", address);
        map.put("Balance", balance );
        map.put("Seed", seedHex);
        map.put("Creationtime", creationTime);
        map.put("Mnemonics", Utils.SPACE_JOINER.join(mnemonics));
        return map;
    }
}
