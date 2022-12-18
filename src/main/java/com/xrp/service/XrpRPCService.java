package com.xrp.service;

import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClient;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.JsonRpcRequest;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.XrplRequestParams;
import org.xrpl.xrpl4j.model.transactions.SetRegularKey;
import org.xrpl.xrpl4j.wallet.Wallet;

@Service
public class XrpRPCService {

    public XrplClient xrplClient;

    public JsonRpcClient jsonRpcClient;

    @Value("${xrp.url}")
    private static String url;

    private void init(){
        if (jsonRpcClient == null && xrplClient == null) {
            xrplClient = new XrplClient(HttpUrl.get(url));
            jsonRpcClient = xrplClient.getJsonRpcClient();
        }
    }

    public JsonRpcRequest jsonRpcRequest(Wallet wallet) throws JSONException, JsonRpcClientErrorException {
        init();
        SetRegularKey setRegularKey = SetRegularKey
                .builder()
                .signingPublicKey(wallet.publicKey())
                .account(wallet.classicAddress())
                .fee(xrplClient.fee().drops().baseFee())
                .build();
        return null;
    }
}
