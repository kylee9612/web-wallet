package com.axia.common.util;

import com.axia.model.vo.NodeConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class RPCClient {
    private final Logger log = LogManager.getLogger(this.getClass());
    private final CloseableHttpClient provider;
    private final NodeConfig nodeConfig;

    public RPCClient(NodeConfig nodeConfig) {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        provider = HttpClients.custom().setConnectionManager(connManager).build();
        this.nodeConfig = nodeConfig;
    }
    public String execute(String reqMethod, String reqPayload) throws Exception {
        CloseableHttpResponse response = null;

        String var6;
        try {
            response = this.provider.execute(this.getNewRequest(reqMethod, reqPayload), new BasicHttpContext());
            response = this.checkResponse(response);
            HttpEntity respPayloadEntity = response.getEntity();
            String respPayload = "";
            if (respPayloadEntity != null) {
                respPayload = EntityUtils.toString(respPayloadEntity);
                EntityUtils.consume(respPayloadEntity);
            }

            log.debug("-- execute(..): '{}' response payload received for HTTP '{}' request with status line '{}'", new Object[]{respPayloadEntity == null ? "null" : "non-null", reqMethod, response.getStatusLine()});
            var6 = respPayload;
        } catch (URISyntaxException | IOException var17) {
            throw new Exception(var17.getMessage());
        } finally {
            if (response != null) {
                try {
                    log.debug("-- execute(..): attempting to recycle old HTTP response (reply to a '{}' request) with status line '{}'", reqMethod, response.getStatusLine());
                    response.close();
                } catch (IOException var16) {
                    log.warn("<< execute(..): failed to recycle old HTTP response, message was: '{}'", var16.getMessage());
                }
            }
        }
        return var6;
    }

    private HttpRequestBase getNewRequest(String reqMethod, String reqPayload) throws URISyntaxException, UnsupportedEncodingException {
        if (reqMethod.equals("POST")) {
            HttpPost postRequest = new HttpPost();
            postRequest.setEntity(new StringEntity(reqPayload, ContentType.create(DataFormats.JSON.getMediaType(), Constants.UTF_8)));
            HttpRequestBase request = postRequest;
            postRequest.setURI(new URI(nodeConfig.getUri()));
            String authScheme = nodeConfig.getRpc_auth();
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

    private String getCredentials(String auth_scheme){
        if (auth_scheme.equals("Basic")) {
            return Base64.encodeBase64String((nodeConfig.getRpc_user() + ":" + nodeConfig.getRpc_password()).getBytes());
        } else{
            return "";
        }
    }

    private CloseableHttpResponse checkResponse(CloseableHttpResponse response) throws Exception {
        log.debug(">> checkResponse(..): checking HTTP response for non-OK status codes & unexpected header values");
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() >= 400 && statusLine.getStatusCode() <= 499) {
            throw new Exception("The server responded with a non-OK (4xx) HTTP status code. Status line: "+statusLine);
        } else if (statusLine.getStatusCode() == 500) {
            return response;
        } else if (statusLine.getStatusCode() >= 501 && statusLine.getStatusCode() <= 599) {
            throw new Exception("The server responded with a non-OK (5xx) HTTP status code. Status line: "+statusLine);
        } else {
            return response;
        }
    }
}
