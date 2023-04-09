package com.axia.btc.controller;

import com.axia.btc.service.BtcService;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.Wallet;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(BTCController.class)
public class BTCControllerTest {

    @MockBean
    private BtcService btcService;
    @InjectMocks
    private BTCController btcController;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        btcController = new BTCController();
        mockMvc = MockMvcBuilders.standaloneSetup(btcController).build();
        ReflectionTestUtils.setField(btcController, "btcService", btcService);
    }

    @Test
    public void testGenerate() throws Exception {
        Wallet wallet = Wallet.createDeterministic(TestNet3Params.get(), Script.ScriptType.P2WPKH);
        ECKey key = new ECKey();
        Map<String, Object> walletMap = new HashMap<>();
        walletMap.put("address", Address.fromKey(TestNet3Params.get(), key, Script.ScriptType.P2WPKH).toString());
        walletMap.put("privateKey", key.getPrivateKeyAsHex());
        walletMap.put("publicKey", key.getPublicKeyAsHex());

        given(btcService.generateWallet()).willReturn(wallet);
        given(btcService.walletFromPrivateKey(any(ECKey.class))).willReturn(walletMap);

        mockMvc.perform(get("/api/v2/btc/generate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").isString())
                .andExpect(jsonPath("$.privateKey").isString())
                .andExpect(jsonPath("$.publicKey").isString());

        verify(btcService, times(1)).generateWallet();
        verify(btcService, times(1)).walletFromPrivateKey(any(ECKey.class));
    }

    @Test
    public void testGetWallet() throws Exception {
        ECKey key = new ECKey();
        Map<String, Object> walletMap = new HashMap<>();
        walletMap.put("address", Address.fromKey(TestNet3Params.get(), key, Script.ScriptType.P2WPKH).toString());
        walletMap.put("privateKey", key.getPrivateKeyAsHex());
        walletMap.put("publicKey", key.getPublicKeyAsHex());

        given(btcService.walletFromPrivateKey(any(ECKey.class))).willReturn(walletMap);

        mockMvc.perform(post("/api/v2/btc/wallet")
                        .param("privateKey", key.getPrivateKeyAsHex()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").isString())
                .andExpect(jsonPath("$.privateKey").isString())
                .andExpect(jsonPath("$.publicKey").isString());

        verify(btcService, times(1)).walletFromPrivateKey(any(ECKey.class));
    }

//    @Test
//    public void testDeriveAddress() throws Exception {
//        String publicKey = "033f6cbe95d161f57c4fc8de383fc4d1174e4db4c6b0113fca3aeb71c2c496d98c";
//        String address = "mqBUudnwDbmJgjjmZBrt1CztmLwFzJxKvM";
//        given(btcService.deriveAddress(anyString())).willReturn(address);
//
//        mockMvc.perform(post("/api/v2/btc/address")
//                        .param("publicKey", publicKey))
//                .andExpect(status().isOk())
//                .andExpect(content().string(address));
//
//        verify(btcService, times(1)).deriveAddress(anyString());
//    }
}