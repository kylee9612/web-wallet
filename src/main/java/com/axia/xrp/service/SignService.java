package com.axia.xrp.service;

import com.google.common.primitives.UnsignedInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.codec.addresses.VersionType;
import org.xrpl.xrpl4j.crypto.KeyMetadata;
import org.xrpl.xrpl4j.crypto.PrivateKey;
import org.xrpl.xrpl4j.crypto.PublicKey;
import org.xrpl.xrpl4j.crypto.signing.DerivedKeysSignatureService;
import org.xrpl.xrpl4j.crypto.signing.SignedTransaction;
import org.xrpl.xrpl4j.crypto.signing.SingleKeySignatureService;
import org.xrpl.xrpl4j.keypairs.DefaultKeyPairService;
import org.xrpl.xrpl4j.keypairs.KeyPairService;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Payment;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;
import org.xrpl.xrpl4j.wallet.Wallet;

@Service
public class SignService {
    private static final Logger log = LogManager.getLogger(SignService.class);
    public SignedTransaction<Payment> signUsingSingleKeySignatureService(Wallet wallet) {
        PrivateKey privateKey = PrivateKey.fromBase16EncodedPrivateKey(wallet.privateKey().get());
        SingleKeySignatureService signatureService = new SingleKeySignatureService(privateKey);

        Payment payment = constructPayment(wallet.classicAddress(), signatureService.getPublicKey(KeyMetadata.EMPTY));
        SignedTransaction<Payment> signedPayment = signatureService.sign(KeyMetadata.EMPTY, payment);
        log.info("Signed Payment: " + signedPayment.signedTransaction());
        return signedPayment;
    }

    public void signUsingDerivedKeysSignatureService() {
        KeyPairService keyPairService = DefaultKeyPairService.getInstance();
        DerivedKeysSignatureService signatureService = new DerivedKeysSignatureService("shh"::getBytes, VersionType.ED25519);

        String walletId = "sample-wallet";
        KeyMetadata keyMetadata = KeyMetadata.builder()
                .platformIdentifier("jks")
                .keyringIdentifier("n/a")
                .keyIdentifier(walletId)
                .keyVersion("1")
                .keyPassword("password")
                .build();

        final PublicKey publicKey = signatureService.getPublicKey(keyMetadata);
        final Address classicAddress = keyPairService.deriveAddress(publicKey.value());

        final Payment payment = constructPayment(classicAddress, publicKey);

        final SignedTransaction<Payment> signedPayment = signatureService.sign(keyMetadata, payment);
        log.info("Signed Payment: " + signedPayment.signedTransaction());
    }
    private Payment constructPayment(Address address, PublicKey publicKey) {
        return Payment.builder()
                .account(address)
                .destination(Address.of("rPT1Sjq2YGrBMTttX4GZHjKu9dyfzbpAYe"))
                .amount(XrpCurrencyAmount.ofDrops(1000))
                .fee(XrpCurrencyAmount.ofDrops(10))
                .sequence(UnsignedInteger.valueOf(16126889))
                .signingPublicKey(publicKey.base16Encoded())
                .build();
    }
}
