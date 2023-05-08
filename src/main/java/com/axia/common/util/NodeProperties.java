package com.axia.common.util;

public enum NodeProperties {
    RPC_PROTOCOL("node.bitcoind.rpc.protocol", "http"),
    RPC_HOST("node.bitcoind.rpc.host", "210.180.118.82"),
    RPC_PORT("node.bitcoind.rpc.port", "8332"),
    RPC_USER("node.bitcoind.rpc.user", "user"),
    RPC_PASSWORD("node.bitcoind.rpc.password", "1234"),
    HTTP_AUTH_SCHEME("node.bitcoind.http.auth_scheme", "Basic"),
    ALERT_PORT("node.bitcoind.notification.alert.port", "5158"),
    BLOCK_PORT("node.bitcoind.notification.block.port", "5159"),
    WALLET_PORT("node.bitcoind.notification.wallet.port", "5160");

    private final String key;
    private final String defaultValue;

    private NodeProperties(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return this.key;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String toString() {
        return "NodeProperties(key=" + this.getKey() + ", defaultValue=" + this.getDefaultValue() + ")";
    }
}
