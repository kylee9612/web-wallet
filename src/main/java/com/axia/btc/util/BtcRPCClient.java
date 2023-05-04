package com.axia.btc.util;

import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.xrpl.xrpl4j.client.JsonRpcClient;

@Component
public class BtcRPCClient {

    @Autowired
    private Environment env;

    private final JsonRpcClient rpcClient;

    public BtcRPCClient() {
        rpcClient = JsonRpcClient.construct(HttpUrl.get(""));
    }
}
