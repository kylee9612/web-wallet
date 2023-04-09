package com.axia.xrp.controller;

import com.axia.dao.master.XrpAccountRepo;
import com.axia.model.vo.XrpAccount;
import com.axia.xrp.service.XrpClientService;
import com.axia.xrp.service.XrpWalletService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.xrpl.xrpl4j.codec.addresses.UnsignedByteArray;
import org.xrpl.xrpl4j.crypto.core.keys.PrivateKey;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.wallet.Wallet;

import java.math.BigDecimal;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(XrpController.class)
public class XrpControllerTest {

    @Mock
    private XrpClientService xrpClientService;

    @Mock
    private XrpWalletService xrpWalletService;

    @Mock
    private XrpAccountRepo xrpAccountRepo;

    @InjectMocks
    private XrpController xrpController;

    private Address testAddress;
    private int testTag;
    private Wallet testWallet;
    private String testPrivateKey;
    private String testPublicKey;
    private String testToAddress;
    private String testMemo;
    private BigDecimal testAmount;
    private XrpAccount testAccount;

    @Before
    public void setup() {
        testAddress = Address.of("rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh");
        testTag = 1234;
        testPrivateKey = "a6d1a6c80d6e98a6f1e2d6b6ee196a2cbda80c4b2e4c37f16d52ec4d4f4c4cb9";
        testPublicKey = "a6d1a6c80d6e98a6f1e2d6b6ee196a2cbda80c4b2e4c37f16d52ec4d4f4c4cb9";
        testToAddress = "rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh";
        testMemo = "Test Memo";
        testAmount = BigDecimal.valueOf(1000);
        testWallet = xrpWalletService.getWallet(PrivateKey.of(UnsignedByteArray.of(testPrivateKey.getBytes())));
        testAccount = new XrpAccount();
        testAccount.setAddress(testAddress.toString());
        testAccount.setDestination(testTag);
        testAccount.setBalance(BigDecimal.valueOf(1000));
    }

    @Test
    public void generateTest() {
        // Mocking
        when(xrpWalletService.generateWallet()).thenReturn(testWallet);
        when(xrpClientService.checkBalance(testAddress)).thenReturn("1000");

        // Test
        Map<String, Object> result = xrpController.generate();
        assertNotNull(result);
        assertEquals(testAddress.toString(), result.get("address"));
        assertEquals(testPrivateKey, result.get("private_key"));
        assertEquals(testPublicKey, result.get("public_key"));
        assertEquals(testAddress.toString() + " (Destination Tag) 1234123412", result.get("qr_code"));
        assertEquals("12341234", result.get("destination"));

        // Verification
        verify(xrpWalletService).generateWallet();
        verify(xrpClientService).checkBalance(testAddress);
    }

    @Test
    public void getWalletTest() {
        // Mocking
        PrivateKey privateKey = any(PrivateKey.class);
        when(xrpWalletService.getWallet(privateKey)).thenReturn(testWallet);

        // Test
        Map<String, Object> result = xrpController.getWallet(testPrivateKey);
        assertNotNull(result);
        assertEquals(testAddress.toString(), result.get("address"));
        assertEquals(testPrivateKey, result.get("private_key"));
        assertEquals(testPublicKey, result.get("public_key"));

        // Verification
        verify(xrpWalletService).getWallet(privateKey);
    }
}