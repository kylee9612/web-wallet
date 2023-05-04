package com.axia.btc.util;

import com.axia.common.util.Constants;
import com.axia.common.util.DataFormats;
import okhttp3.HttpUrl;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.xrpl.xrpl4j.client.JsonRpcClient;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@Component
public class BtcRPCClient {
    private final Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private Environment env;

    private CloseableHttpClient provider;
    private Properties nodeConfig;

    private final JsonRpcClient rpcClient;

    public BtcRPCClient() {
        rpcClient = JsonRpcClient.construct(HttpUrl.get(""));
    }


    private HttpRequestBase getNewRequest(String reqMethod, String reqPayload) throws URISyntaxException, UnsupportedEncodingException {
        if (reqMethod.equals("POST")) {
            HttpPost postRequest = new HttpPost();
            postRequest.setEntity(new StringEntity(reqPayload, ContentType.create(DataFormats.JSON.getMediaType(), Constants.UTF_8)));
            HttpRequestBase request = postRequest;
            postRequest.setURI(new URI(String.format("%s://%s:%s/", this.nodeConfig.getProperty(NodeProperties.RPC_PROTOCOL.getKey()), this.nodeConfig.getProperty(NodeProperties.RPC_HOST.getKey()), this.nodeConfig.getProperty(NodeProperties.RPC_PORT.getKey()))));
            String authScheme = this.nodeConfig.getProperty(NodeProperties.HTTP_AUTH_SCHEME.getKey());
            request.addHeader(this.resolveAuthHeader(authScheme));
            log.debug("<< getNewRequest(..): returning a new HTTP '{}' request with target endpoint '{}' and headers '{}'", new Object[]{reqMethod, request.getURI(), request.getAllHeaders()});
            return request;
        } else {
            throw new IllegalArgumentException("Expected the argument to be a valid HTTP method, but was invalid/unsupported instead.");
        }
    }

    private Header resolveAuthHeader(String authScheme) {
        if (authScheme.equals("")) {
            return null;
        } else {
            return authScheme.equals("Basic") ? new BasicHeader("Authorization", "Basic " + this.getCredentials("Basic")) : null;
        }
    }

    private String getCredentials(String authScheme) {
        if (authScheme.equals("")) {
            return "";
        } else if (authScheme.equals("Basic")) {
            return Base64.encodeBase64String((this.nodeConfig.getProperty(NodeProperties.RPC_USER.getKey()) + ":" + this.nodeConfig.getProperty(NodeProperties.RPC_PASSWORD.getKey())).getBytes());
        } else {
            throw new IllegalArgumentException("Expected the argument to be a valid HTTP auth scheme, but was invalid/unsupported instead.");
        }
    }
}
