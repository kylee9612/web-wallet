package com.axia.xrp.util;

import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.transactions.Address;

public class XrpRequestParamUtil {

    public AccountInfoRequestParams getAccountInfoRequest(Address classicAddress) {
        return AccountInfoRequestParams
                .builder().ledgerIndex(LedgerIndex.VALIDATED)
                .account(classicAddress)
                .build();
    }
}
