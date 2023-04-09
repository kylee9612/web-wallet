package com.axia.xrp.controller;

import com.axia.common.controller.CoinController;
import com.axia.common.util.QRUtil;
import com.axia.dao.master.XrpAccountRepo;
import com.axia.model.vo.XrpAccount;
import com.axia.xrp.service.XrpAccountService;
import com.axia.xrp.service.XrpClientService;
import com.axia.xrp.service.XrpWalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.xrpl.xrpl4j.codec.addresses.UnsignedByteArray;
import org.xrpl.xrpl4j.crypto.core.keys.PrivateKey;
import org.xrpl.xrpl4j.crypto.core.keys.PublicKey;
import org.xrpl.xrpl4j.model.transactions.*;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/xrp")
public class XrpController extends CoinController {

    private static final Logger log = LogManager.getLogger(XrpController.class);

    @Value("${xrp.test}")
    private boolean isTest;
    @Autowired
    private XrpAccountRepo xrpAccountRepo;
    @Autowired
    private XrpClientService xrpClientService;
    @Autowired
    private XrpWalletService xrpWalletService;
    @Autowired
    private XrpAccountService xrpAccountService;


    public Map<String, Object> getInfo() {
        return xrpClientService.getInfo();
    }

    public void fundFaucet(Address classicAddress) {
        xrpClientService.fundFaucet(classicAddress);
    }

    public void fundFaucet(Address classicAddress, int tag){
        String address = classicAddress.toString();
        xrpClientService.fundFaucet(classicAddress);
        XrpAccount account = xrpAccountRepo
                .findFirstByAddressAndDestination(address, tag)
                .orElseThrow();
        BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(xrpClientService.checkBalance(classicAddress, tag)) + 1000);
        account.setBalance(balance);
        xrpAccountRepo.save(account);
    }

    @Override
    @GetMapping("/generate")
    public Map<String, Object> generate() {
        Wallet wallet = xrpWalletService.generateWallet();
        if(isTest) {
            fundFaucet(wallet.classicAddress());
        }
        Map<String, Object> map = xrpWalletService.walletToMap(wallet);
        map.put("destination","12341234");
        String url = wallet.classicAddress() + " (Destination Tag) "+"1234123412";
        QRUtil.generateQR(url,map);
        return map;
    }

    @Override
    @PostMapping("/wallet")
    public Map<String, Object> getWallet(@RequestParam("private_key") String privateKey) {
        Wallet wallet = xrpWalletService.getWallet(PrivateKey.of(UnsignedByteArray.of(privateKey.getBytes())));
        Map<String, Object> map = xrpWalletService.walletToMap(wallet);
        return map;
    }

    @Override
    @PostMapping("/address")
    public String deriveAddress(@RequestParam("public_key") String publicKey) {
        return xrpWalletService.deriveAddress(PublicKey.fromBase16EncodedPublicKey(publicKey));
    }

    @Override
    @PostMapping("/public")
    public String derivePublicKey(@RequestParam("private_key")String privateKey) {
        return xrpWalletService.derivePublicKey(PrivateKey.of(UnsignedByteArray.of(privateKey.getBytes())));
    }

    @Override
    @PostMapping("/send")
    public Map<String, Object> sendToAddress(@RequestParam("privateKey") String privateKey,
                                             @RequestParam("toAddress") String toAddress,
                                             @RequestParam("memo") String memo,
                                             @RequestParam("amount") BigDecimal amount) throws Exception {
        Wallet wallet = xrpWalletService.getWallet(PrivateKey.of(UnsignedByteArray.of(privateKey.getBytes())));
        xrpClientService.sendXRP(wallet,toAddress,memo,amount);
        return checkBalance(String.valueOf(wallet.classicAddress()));
    }

    @Override
    public Map<String, Object> checkBalance(String address) {
        String balance = xrpClientService.checkBalance(Address.of(address));
        Map<String, Object> map = new HashMap<>();
        map.put("balance",balance);
        return map;
    }
}
