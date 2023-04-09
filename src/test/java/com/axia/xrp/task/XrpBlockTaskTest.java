package com.axia.xrp.task;

import com.axia.common.task.AbstractDemonTask;
import com.axia.dao.master.XrpAccountRepo;
import com.axia.dao.master.XrpWalletRepo;
import okhttp3.HttpUrl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.ledger.LedgerResult;
import org.xrpl.xrpl4j.model.client.transactions.TransactionResult;
import org.xrpl.xrpl4j.model.ledger.LedgerHeader;
import org.xrpl.xrpl4j.model.transactions.Payment;
import org.xrpl.xrpl4j.model.transactions.Transaction;

import java.util.List;

public class XrpBlockTaskTest extends AbstractDemonTask {

    private static final Logger log = LogManager.getLogger(XrpBlockTaskTest.class);

    private final String clientAddress;
    private final String walletAddress;
    private int lastLedgerIndex;

    private XrplClient xrplClient;

    private final XrpWalletRepo xrpWalletRepo;
    private final XrpAccountRepo xrpAccountRepo;

    public XrpBlockTaskTest(String url, String walletAddress, XrpWalletRepo xrpWalletRepo, XrpAccountRepo xrpAccountRepo) {
        this.clientAddress = url;
        this.walletAddress = walletAddress;
        this.xrpWalletRepo = xrpWalletRepo;
        this.xrpAccountRepo = xrpAccountRepo;
        super.sleepTime = 1000;
    }

    @Override
    protected void execute() throws Exception {
        openDBNode();
        LedgerResult result = xrplClient.ledger(
                LedgerRequestParams.builder()
                        .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                        .transactions(true)
                        .build());
        int ledgerIndex = result.ledgerIndex().get().unsignedIntegerValue().intValue();
        lastLedgerIndex = lastLedgerIndex == 0 ? ledgerIndex : lastLedgerIndex;
        if (ledgerIndex > lastLedgerIndex) {
            lastLedgerIndex = ledgerIndex;
            log.info("Ledger Index : " + result.ledger().ledgerIndex());

            LedgerHeader header = result.ledger();
            List<TransactionResult<? extends Transaction>> transactions = header.transactions();
            for (TransactionResult tr : transactions) {
                if (tr.transaction() instanceof Payment payment) {
                    log.info(payment);
                    log.info("Tx Hash : " + payment.hash());
                    log.info("Destination : " + payment.destination());
                    log.info("Account From : " + payment.account());
                    log.info("Amount :" + payment.amount());
                    log.info("Tag : " + payment.destinationTag());
                    log.info("Source Tag : " + payment.sourceTag());
                    if(payment.destination().toString().equals(walletAddress)){
                        XrpReceiveTaskTest xrpReceiveTaskTest = new XrpReceiveTaskTest(payment,walletAddress,xrpWalletRepo,xrpAccountRepo);
                        xrpReceiveTaskTest.start();
                    }
                }
            }
        }
        try{
            sleep(1000);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    protected void openDBNode() {
        if (xrplClient == null)
            xrplClient = new XrplClient(HttpUrl.get(clientAddress));
    }

    @Override
    protected void closeDBNode() {
        xrplClient = null;
    }
}
