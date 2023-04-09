package com.axia.btc.controller;

import com.axia.btc.service.BtcService;
import com.axia.common.controller.CoinController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
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
        BigInteger privateKey = Objects.requireNonNull(wallet.getActiveKeyChain().getRootKey()).getPrivKey();
        ECKey key = ECKey.fromPrivate(privateKey);
        Map<String, Object> walletMap = btcService.walletFromPrivateKey(key);
        log.info("BTC Address Generated ::: " + walletMap.get("address"));
        return walletMap;
    }

    @Override
    @PostMapping("/wallet")
    public Map<String, Object> getWallet(@RequestParam("privateKey") String privateKey) {
        ECKey key = ECKey.fromPrivate(Utils.HEX.decode(privateKey));
        Map<String, Object> walletMap = btcService.walletFromPrivateKey(key);
        log.info("BTC Address Formed ::: " + walletMap.get("address"));
        return walletMap;
    }


    /**
     * 2023-03-29
     * TODO
     * public key 에서 address 추출
     * private key 에서 public key 추출
     * ECKey 사용
     */
    @Override
    @PostMapping("/address")
    public String deriveAddress(@RequestParam("publicKey") String publicKey) {
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
     */

    @Override
    @PostMapping("/send")
    public Map<String, Object> sendToAddress(@RequestParam("privateKey") String privateKey,
                                             @RequestParam("toAddress") String toAddress,
                                             @RequestParam("memo") String memo,
                                             @RequestParam("amount") BigDecimal amount) throws Exception{
        ECKey key = ECKey.fromPrivate(Utils.HEX.decode(privateKey));
        long sendAmount = amount.multiply(BigDecimal.valueOf(10000000)).longValue();
        Transaction rawTransaction = btcService.createRawTransaction(key, toAddress, sendAmount);
        btcService.signTransaction(rawTransaction, key);
        btcService.broadcastTransaction(rawTransaction);
        Map<String, Object> rtnMap = checkBalance(toAddress);
        rtnMap.put("transaction", rawTransaction);
        return rtnMap;
    }

    /**
     * 2023-03-29
     * TODO
     * UTXO list 불러옴과 같이,
     * 잔액 총 합 return
     */

    @Override
    @PostMapping("/balance")
    public Map<String, Object> checkBalance(@RequestParam("address") String address) {
//        ECKey key = ECKey.fromPrivate(Utils.HEX.decode(address));
        BigDecimal balance = btcService.getBalance(address);
        HashMap<String,Object> map = new HashMap<>();
        map.put("balance",balance);
        return map;
    }
}
