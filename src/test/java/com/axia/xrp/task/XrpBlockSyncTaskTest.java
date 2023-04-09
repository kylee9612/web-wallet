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

public class XrpBlockSyncTaskTest extends AbstractDemonTask {
    private final Logger log = LogManager.getLogger(XrpBlockSyncTaskTest.class);
    private final String clientAddress;
    private final String walletAddress;
    private int lastLedgerIndex;

    private XrplClient xrplClient;

    private final XrpWalletRepo xrpWalletRepo;
    private final XrpAccountRepo xrpAccountRepo;

    public XrpBlockSyncTaskTest(String url, String walletAddress, XrpWalletRepo xrpWalletRepo, XrpAccountRepo xrpAccountRepo, int lastLedgerIndex) {
        this.clientAddress = url;
        this.walletAddress = walletAddress;
        this.xrpWalletRepo = xrpWalletRepo;
        this.xrpAccountRepo = xrpAccountRepo;
        this.lastLedgerIndex = lastLedgerIndex;
        super.sleepTime = 1000;
    }

    @Override
    protected void execute() throws Exception {
        openDBNode();
        int currentIndex = 0;
        while (currentIndex != lastLedgerIndex) {
            LedgerResult result = xrplClient.ledger(
                    LedgerRequestParams.builder()
                            .ledgerSpecifier(LedgerSpecifier.of(currentIndex))
                            .transactions(true)
                            .build());
            log.info("Sync Task Ledger Index : " + result.ledger().ledgerIndex());

            LedgerHeader header = result.ledger();
            currentIndex++;
            List<TransactionResult<? extends Transaction>> transactions = header.transactions();
            for (TransactionResult tr : transactions) {
                if (tr.transaction() instanceof Payment payment) {
                    if (payment.destination().toString().equals(walletAddress)) {
                        log.info(payment);
                        log.info("Sync Task Tx Hash : " + payment.hash());
                        log.info("Sync Task Destination : " + payment.destination());
                        log.info("Sync Task Account From : " + payment.account());
                        log.info("Sync Task Amount :" + payment.amount());
                        log.info("Sync Task Tag : " + payment.destinationTag());
                        log.info("Sync Task Source Tag : " + payment.sourceTag());
                        XrpReceiveTaskTest xrpReceiveTaskTest = new XrpReceiveTaskTest(payment, walletAddress, xrpWalletRepo, xrpAccountRepo);
                        xrpReceiveTaskTest.start();
                    }
                }
            }
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
