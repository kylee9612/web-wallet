package com.axia.btc.controller;

import com.axia.btc.service.BtcService;
import com.axia.common.controller.CoinController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v2/btc")
public class BTCController extends CoinController {

    private static final Logger log = LogManager.getLogger(BTCController.class);

    @Autowired
    private BtcService btcService;
    @Override
    @GetMapping("/generate")
    public Map<String, Object> generate() {
        Wallet wallet = btcService.generateWallet();
        Map<String, Object> walletMap = new HashMap<>();
        ECKey key = ECKey.fromPrivate(Objects.requireNonNull(wallet.getActiveKeyChain().getRootKey()).getPrivKey());
        walletMap.put("Private Key", key.getPrivateKeyAsHex());
        walletMap.put("Public Key", key.getPublicKeyAsHex());
        String address = Address.fromKey(MainNetParams.fromID(MainNetParams.ID_TESTNET),key, Script.ScriptType.P2WPKH).toString();
        walletMap.put("Address", address);
        log.info("BTC Address Generated ::: "+address);
        return walletMap;
    }

    @Override
    @PostMapping("/wallet")
    public Map<String, Object> getWallet(@RequestParam("privateKey") String privateKey) {
        Map<String, Object> walletMap = new HashMap<>();
        ECKey key = ECKey.fromPrivate(Utils.HEX.decode(privateKey));
        walletMap.put("Private Key", key.getPrivateKeyAsHex());
        walletMap.put("Public Key", key.getPublicKeyAsHex());
        String address = Address.fromKey(MainNetParams.fromID(MainNetParams.ID_TESTNET),key, Script.ScriptType.P2WPKH).toString();
        walletMap.put("Address", address);
        log.info("BTC Address Formed ::: "+address);
        return walletMap;
    }


    /**
     * 2023-03-29
     * TODO
     * public key 에서 address 추출
     * private key 에서 public key 추출
     * ECKey 사용
     * */
    @Override
    @PostMapping("/address")
    public String deriveAddress(@RequestParam("publicKey")String publicKey) {
        return null;
    }

    @Override
    @PostMapping("/public")
    public String derivePublicKey(@RequestParam("privateKey") String privateKey) {
        return null;
    }


    /**
     * 2023-03-29
     * TODO
     * private key 로  raw transaction 생성 및,
     * transaction sign 기능 추가
     * */

    @Override
    @PostMapping("/send")
    public Map<String, Object> sendToAddress(String privateKey, String toAddress, BigDecimal amount) {
        return null;
    }

    /**
     * 2023-03-29
     * TODO
     * UTXO list 불러옴과 같이,
     * 잔액 총 합 return
     * */

    @Override
    @PostMapping("/balance")
    public Map<String, Object> checkBalance(String address) {
        return null;
    }
}
