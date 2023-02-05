package com.axia.xrp.service;

import com.axia.dao.master.XrpWalletRepo;
import com.axia.xrp.task.XrpBlockTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import com.axia.dao.master.UserRepo;
import com.axia.dao.master.XrpAccountRepo;
import com.axia.model.vo.XrpAccount;
import com.axia.xrp.util.XrpRequestParamUtil;
import okhttp3.HttpUrl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.client.faucet.FaucetClient;
import org.xrpl.xrpl4j.client.faucet.FundAccountRequest;
import org.xrpl.xrpl4j.crypto.KeyMetadata;
import org.xrpl.xrpl4j.crypto.PrivateKey;
import org.xrpl.xrpl4j.crypto.signing.SignatureService;
import org.xrpl.xrpl4j.crypto.signing.SignedTransaction;
import org.xrpl.xrpl4j.crypto.signing.SingleKeySignatureService;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.accounts.AccountTransactionsResult;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.fees.FeeResult;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.serverinfo.ServerInfoResult;
import org.xrpl.xrpl4j.model.client.transactions.SubmitResult;
import org.xrpl.xrpl4j.model.client.transactions.TransactionRequestParams;
import org.xrpl.xrpl4j.model.client.transactions.TransactionResult;
import org.xrpl.xrpl4j.model.immutables.FluentCompareTo;
import org.xrpl.xrpl4j.model.transactions.*;
import org.xrpl.xrpl4j.wallet.Wallet;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class XrpClientService {
    private static final Logger log = LogManager.getLogger(XrpClientService.class);


    @Value("${xrp.url}")
    private String url;
    @Value("${xrp.test}")
    private boolean isTest;

    @Autowired
    private XrpAccountRepo xrpAccountRepo;
    @Autowired
    private XrpWalletRepo xrpWalletRepo;
    @Autowired
    private UserRepo userRepo;

    private XrplClient xrplClient;
    private FaucetClient faucetClient;
    private XrpRequestParamUtil paramUtil;

    @PostConstruct
    private void init() throws Exception {
        xrplClient = new XrplClient(HttpUrl.get(url));
        faucetClient = FaucetClient.construct(HttpUrl.get("https://faucet.altnet.rippletest.net"));
        paramUtil = new XrpRequestParamUtil();
        String walletAddress = xrpAccountRepo.findById(1).get().getAddress();
        XrpBlockTask xrpBlockTask = new XrpBlockTask(url,walletAddress,xrpWalletRepo,xrpAccountRepo);
        xrpBlockTask.start();
    }

    public String checkServer() {
        ServerInfoResult result = null;
        try {
            result = xrplClient.serverInformation();
            log.info(result.toString());
            return result.toString();
        } catch (JsonRpcClientErrorException e) {
            log.error(e.getMessage());
            return null;
        }
    }


    public Map<String, Object> getInfo() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("Server Info", xrplClient.serverInformation());
            map.put("Node Fee", xrplClient.fee());
            map.put("JsonRpcClient", xrplClient.getJsonRpcClient());
            log.info(map + "");
            return map;
        } catch (JsonRpcClientErrorException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void fundFaucet(Address classicAddress) {
        if (isTest) {
            faucetClient.fundAccount(FundAccountRequest.of(classicAddress));
            System.out.println("Funded the account using the Testnet faucet.");
            try {
                Thread.sleep(1000 * 4);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Faucet is not valid on live server");
        }
    }

    public String checkBalance(Address classicAddress, int tag) {
        if(tag != 0) {
            XrpAccount account = xrpAccountRepo.findFirstByAddressAndDestination(classicAddress.toString(), tag).get();
            return account.getBalance().toString();
        }else{
            try {
                return checkBalance(classicAddress);
            }catch (Exception e){
                return null;
            }
        }
    }

    public String checkBalance(Address classicAddress) throws JsonRpcClientErrorException {
        AccountInfoRequestParams requestParams = paramUtil.getAccountInfoRequest(classicAddress);
        AccountInfoResult accountInfoResult = getAccountInfo(requestParams);
        XrpCurrencyAmount balance = accountInfoResult.accountData().balance();
        log.info("Balance : " + balance.toXrp());
        return accountInfoResult.accountData().balance().toXrp().toString();
    }

    public AccountInfoResult getAccountInfo(AccountInfoRequestParams resultParams) throws JsonRpcClientErrorException {
        AccountInfoResult result = xrplClient.accountInfo(resultParams);
        log.info(result.toString());
        return result;
    }

    public AccountTransactionsResult accountTransactionsResult(Address address) {
        try {
            AccountTransactionsResult result = xrplClient.accountTransactions(address);
            log.info(result.toString());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public SetRegularKey getRegularKey(Wallet wallet) {
        SetRegularKey setRegularKey = null;
        try {
            setRegularKey = SetRegularKey
                    .builder()
                    .signingPublicKey(wallet.publicKey())
                    .account(wallet.classicAddress())
                    .fee(xrplClient.fee().drops().baseFee())
                    .build();
            log.info(setRegularKey.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return setRegularKey;
    }

    public FeeResult getFee() throws JsonRpcClientErrorException {
        return xrplClient.fee();
    }

    public void sendXRP(Wallet testWallet, String addressTo, int tag, BigDecimal bigAmount) throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        Address classicAddress = testWallet.classicAddress();

        AccountInfoRequestParams requestParams = paramUtil.getAccountInfoRequest(classicAddress);
        AccountInfoResult accountInfoResult = getAccountInfo(requestParams);

        final UnsignedInteger sequence = accountInfoResult.accountData().sequence();
        final FeeResult feeResult = xrplClient.fee();
        final XrpCurrencyAmount openLedgerFee = feeResult.drops().openLedgerFee();

        // Construct a Payment
        final LedgerIndex validatedLedger = xrplClient.ledger(LedgerRequestParams.builder().ledgerSpecifier(LedgerSpecifier.VALIDATED).build())
                .ledgerIndex()
                .orElseThrow(() -> new RuntimeException("LedgerIndex not available."));

        final UnsignedInteger lastLedgerSequence = UnsignedInteger.valueOf(
                validatedLedger.plus(UnsignedInteger.valueOf(4)).unsignedIntegerValue().intValue()
        ); // <-- LastLedgerSequence is the current ledger index + 4

        Payment payment;
        if (tag == 0) {
            payment = Payment.builder()
                    .account(classicAddress)
                    .amount(XrpCurrencyAmount.ofXrp(bigAmount))
                    .destination(Address.of(addressTo))
                    .sequence(sequence)
                    .fee(openLedgerFee)
                    .signingPublicKey(testWallet.publicKey())
                    .lastLedgerSequence(lastLedgerSequence)
                    .build();
        }else{
            payment = Payment.builder()
                    .account(classicAddress)
                    .amount(XrpCurrencyAmount.ofXrp(bigAmount))
                    .destination(Address.of(addressTo))
                    .sequence(sequence)
                    .fee(openLedgerFee)
                    .destinationTag(UnsignedInteger.valueOf(tag))
                    .signingPublicKey(testWallet.publicKey())
                    .lastLedgerSequence(lastLedgerSequence)
                    .build();
        }
        log.info("Constructed Payment: " + payment);

        // Construct a SignatureService to sign the Payment
        PrivateKey privateKey = PrivateKey.fromBase16EncodedPrivateKey(testWallet.privateKey().get());
        SignatureService signatureService = new SingleKeySignatureService(privateKey);

        // Sign the Payment
        final SignedTransaction<Payment> signedPayment = signatureService.sign(KeyMetadata.EMPTY, payment);
        log.info("Signed Payment: " + signedPayment.signedTransaction());

        // Submit the Payment
        final SubmitResult<Transaction> submitResult = xrplClient.submit(signedPayment);
        log.info("Submit Result: "+submitResult);

        // Wait for validation
        TransactionResult<Payment> transactionResult = validateTransaction(signedPayment,lastLedgerSequence);

        // Check transaction results
        log.info(transactionResult);
        if (isTest) {
            log.info("Explorer link: https://testnet.xrpl.org/transactions/" + signedPayment.hash());
        } else {
            log.info("Explorer link: https://livenet.xrpl.org/transactions/" + signedPayment.hash());
        }
        transactionResult.metadata().ifPresent(metadata -> {
            log.info("Result code: " + metadata.transactionResult());
            metadata.deliveredAmount().ifPresent(deliveredAmount ->
                    log.info("XRP Delivered: " + ((XrpCurrencyAmount) deliveredAmount).toXrp())
            );
        });
    }

    private TransactionResult<Payment> validateTransaction(SignedTransaction<Payment> signedPayment, UnsignedInteger lastLedgerSequence) throws InterruptedException, JsonRpcClientErrorException {
        TransactionResult<Payment> transactionResult = null;
        boolean transactionValidated = false;
        boolean transactionExpired = false;
        while (!transactionValidated && !transactionExpired) {
            Thread.sleep(4 * 1000);
            final LedgerIndex latestValidatedLedgerIndex = xrplClient.ledger(
                            LedgerRequestParams.builder().ledgerSpecifier(LedgerSpecifier.VALIDATED).build()
                    )
                    .ledgerIndex()
                    .orElseThrow(() -> new RuntimeException("Ledger response did not contain a LedgerIndex."));

            transactionResult = xrplClient.transaction(
                    TransactionRequestParams.of(signedPayment.hash()),
                    Payment.class);

            if (transactionResult.validated()) {
                log.info("Payment was validated with result code " + transactionResult.metadata().get().transactionResult());
                transactionValidated = true;
            } else {
                final boolean lastLedgerSequenceHasPassed = FluentCompareTo.
                        is(latestValidatedLedgerIndex.unsignedLongValue())
                        .greaterThan(UnsignedLong.valueOf(lastLedgerSequence.intValue()));
                if (lastLedgerSequenceHasPassed) {
                    log.info("LastLedgerSequence has passed. Last tx response: " +
                            transactionResult);
                    transactionExpired = true;
                } else {
                    log.info("Payment not yet validated.");
                }
            }
        }
        return transactionResult;
    }
}
