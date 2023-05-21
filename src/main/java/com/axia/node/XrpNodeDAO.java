package com.axia.node;

import com.axia.dao.master.XrpWalletRepo;
import com.axia.dao.slave.config.NodeConfigSlaveRepo;
import okhttp3.HttpUrl;
import org.springframework.stereotype.Repository;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.crypto.bc.keys.BcKeyUtils;
import org.xrpl.xrpl4j.crypto.bc.wallet.BcWalletFactory;
import org.xrpl.xrpl4j.crypto.core.keys.Seed;
import org.xrpl.xrpl4j.crypto.core.wallet.Wallet;
import org.xrpl.xrpl4j.crypto.core.wallet.WalletFactory;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.server.ServerInfo;
import org.xrpl.xrpl4j.model.client.server.ServerInfoLedger;
import org.xrpl.xrpl4j.model.transactions.Address;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XrpNodeDAO extends AbstractNodeDAO {
    private final XrplClient xrplClient;
    private final WalletFactory walletFactory = BcWalletFactory.getInstance();
    private XrpWalletRepo walletRepo;

    public XrpNodeDAO(String coinName, NodeConfigSlaveRepo repo) {
        super(coinName, repo);
        xrplClient = new XrplClient(HttpUrl.get(nodeConfig.getUri()));
    }

    @Override
    public Map<String, Object> newAddress(Map<String, Object> param) {
        Wallet wallet = walletFactory.fromSeed(Seed.ed25519Seed());
        Map<String, Object> walletMap = new HashMap<>();
        walletMap.put("address",wallet.address().toString());
        walletMap.put("publicKey",wallet.publicKey().base16Value());
        walletMap.put("privateKey",wallet.privateKey().sha256());
        return walletMap;
    }

    @Override
    public boolean isValidAddress(String address, String memo) {
        try {
            AccountInfoResult result = xrplClient.accountInfo(AccountInfoRequestParams
                    .builder()
                    .account(Address.of(address))
                    .build());
            if(!result.validated()){
                return false;
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Map<String, Object> getBalance(String address, String memo) {
        return null;
    }

    @Override
    public List<Map<String, Object>> listTransactions(String address, String memo) {
        return null;
    }

    @Override
    public List<Map<String, Object>> listTransactions(BigInteger blockNumber) {
        return null;
    }

    @Override
    public Map<String, Object> reloadBlock(BigInteger blockNumber) {
        return null;
    }

    @Override
    public Object createRawTransaction(Map<String, Object> param) {
        return null;
    }

    @Override
    public Object signTransaction(Map<String, Object> param) {
        return null;
    }

    @Override
    public Object sendTransaction(Map<String, Object> param) {
        return null;
    }

    @Override
    public Map<String, Object> checkNode() {
        Map<String, Object> map = new HashMap<>();
        try {
            ServerInfo serverInfo = xrplClient.serverInfo();
            map.put("fee",serverInfo.validatedLedger().get().baseFeeXrp().intValue());
            map.put("blockNumber",serverInfo.validatedLedger().get().sequence().toString());
            map.put("version",serverInfo.buildVersion());
            map.put("latency",serverInfo.ioLatencyMs());
        } catch (Exception e) {
            log.error("exception :::"+e);
        }
        return map;
    }
}
