package com.axia.common.controller;

import java.math.BigDecimal;
import java.util.Map;
public abstract class CoinController {

    /**
     * TODO
     * Main Coin Controller 생성하여,
     * mapping url 분석 후,
     * 하위 컨트롤러에 전달 기능 추가
     * */

    public abstract Map<String, Object> generate();
    public abstract Map<String, Object> getWallet(String privateKey);
    public abstract String deriveAddress(String publicKey);
    public abstract String derivePublicKey(String privateKey);
    public abstract Map<String, Object> sendToAddress(String privateKey, String toAddress, String memo, BigDecimal amount) throws Exception;
    public abstract Map<String, Object> checkBalance(String address);
}
