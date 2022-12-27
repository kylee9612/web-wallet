package com.xrp.service;

import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClient;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.JsonRpcRequest;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.XrplRequestParams;
import org.xrpl.xrpl4j.model.client.XrplResult;

import javax.annotation.PostConstruct;

@Service
public class XrpRPCService {
    private static final Logger log = LoggerFactory.getLogger(XrpRPCService.class);

    private XrplClient xrplClient;

    private JsonRpcClient jsonRpcClient;

    @Value("${xrp.url}")
    private String url;

    @PostConstruct
    private void init() {
        if (jsonRpcClient == null && xrplClient == null) {
            xrplClient = new XrplClient(HttpUrl.get(url));
            jsonRpcClient = xrplClient.getJsonRpcClient();
        }
    }

    public XrplResult jsonRpcRequest(String methods, XrplRequestParams params){
        try {
            JsonRpcRequest rpcRequest = JsonRpcRequest
                    .builder()
                    .method(methods)
                    .addParams(params)
                    .build();
            log.info(rpcRequest.toString());
            return jsonRpcClient.send(rpcRequest,XrplResult.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
