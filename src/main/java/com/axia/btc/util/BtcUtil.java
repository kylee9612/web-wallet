package com.axia.btc.util;

import org.bitcoinj.wallet.Wallet;
import org.json.simple.JSONObject;

public class BtcUtil {

    public static JSONObject getWallet(Wallet wallet){
        JSONObject object = new JSONObject();
//        object.put("UTXO Provider",wallet.getUTXOProvider());
//        object.put("",wallet.currentReceiveKey());
        return object;
    }
}
