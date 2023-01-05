package com.axia.xrp.task;

import com.axia.common.task.AbstractDemonTask;
import com.axia.xrp.service.XrpClientService;
import com.google.common.primitives.UnsignedLong;
import okhttp3.HttpUrl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.ledger.LedgerResult;
import org.xrpl.xrpl4j.model.client.transactions.TransactionRequestParams;
import org.xrpl.xrpl4j.model.client.transactions.TransactionResult;
import org.xrpl.xrpl4j.model.ledger.LedgerHeader;
import org.xrpl.xrpl4j.model.transactions.OfferCreate;
import org.xrpl.xrpl4j.model.transactions.Payment;
import org.xrpl.xrpl4j.model.transactions.Transaction;

import javax.annotation.PostConstruct;
import java.util.List;

public class XrpReceiveTask extends AbstractDemonTask {

    private final static Logger log = LogManager.getLogger(XrpReceiveTask.class);

    private XrplClient xrplClient;


    private final String clientAddress;

    public XrpReceiveTask(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    @Override
    protected void execute() throws Exception {
        openDBNode();
        while (true) {
            LedgerResult result = xrplClient.ledger(
                    LedgerRequestParams.builder()
                            .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                            .build());
            log.info(result);

//            TransactionResult transactionResult = getTransactionResult(TransactionRequestParams.of(result.ledger().ledgerHash().get()));
//            log.info(transactionResult);
            LedgerHeader header = result.ledger();
            List<TransactionResult<? extends Transaction>> transactions = header.transactions();
            for (TransactionResult tr : transactions) {
                Transaction transaction = tr.transaction();
                log.info("TxID" + transaction.accountTransactionId().get());
                log.info("Account From : " + transaction.account());
                log.info("Tag : " + transaction.sourceTag());
                log.info("Tx : " + transaction);
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                return;
            }
        }
    }

    private TransactionResult getTransactionResult(TransactionRequestParams params) {
        try {
            Thread.sleep(1000);
            return xrplClient.transaction(params, Transaction.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(params.transaction());
            return getTransactionResult(params);
        }
    }

    @Override
    protected void openDBNode() {
        if (xrplClient == null)
            xrplClient = new XrplClient(HttpUrl.get(clientAddress));
    }

    @Override
    protected void closeDBNode() {
        if (xrplClient != null)
            xrplClient = null;
    }
}
