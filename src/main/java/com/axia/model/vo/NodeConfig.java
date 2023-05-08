package com.axia.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "node_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NodeConfig {
    @Id
    private String coin;
    private String protocol;
    private String port;
    private String host;
    private String rpc_user;
    private String rpc_password;
    private String rpc_auth;

    public NodeConfig(String coinName) {
        this.coin = coinName;
        protocol = "http";
        switch (coin) {
            case "BTC" -> {
                host = "testnet-seed.bluematt.me";
                port = "18333";
            }
            case "ETH" -> {
                host = "172.27.3.58";
                port = "8333";
            }
            case "XRP" -> {
                protocol = "https";
                host = "s.altnet.rippletest.net";
                port = "51234";
            }
            case "BNB" -> {
                protocol = "https";
                host = "data-seed-prebsc-1-s1.binance.org";
                port = "8545";
            }
        }
    }

    public Header resolveHeader() {
        return rpc_auth.equals("Basic") ? new BasicHeader("Authorization", "Basic " + this.getCredentials("Basic")) : null;
    }

    public String getCredentials(String auth_scheme) {
        if (auth_scheme.equals("Basic")) {
            return Base64.encodeBase64String((rpc_user + ":" + rpc_password).getBytes());
        } else {
            return "";
        }
    }

    public String getUri() {
        return String.format("%s://%s:%s", protocol, host, port);
    }
}
