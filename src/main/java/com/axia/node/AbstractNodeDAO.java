package com.axia.node;

import com.axia.dao.slave.config.NodeConfigSlaveRepo;
import com.axia.model.vo.NodeConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public abstract class AbstractNodeDAO {

    protected final Logger log = LogManager.getLogger(this.getClass());
    protected NodeConfigSlaveRepo nodeRepo;
    protected NodeConfig nodeConfig;
    protected String coinName;

    public AbstractNodeDAO(String coinName, NodeConfigSlaveRepo nodeRepo) {
        this.nodeRepo = nodeRepo;
        this.coinName = coinName;
        nodeRepo.findById(coinName).ifPresentOrElse(
                nodeConfig -> this.nodeConfig = nodeConfig,
                () -> this.nodeConfig = new NodeConfig(this.coinName));
    }

    public abstract Map<String, Object> newAddress(Map<String, Object> param);

    public abstract boolean isValidAddress(String address, String memo);

    public abstract Map<String, Object> getBalance(String address, String memo);

    public abstract List<Map<String, Object>> listTransactions(String address, String memo);

    public abstract List<Map<String, Object>> listTransactions(BigInteger blockNumber);

    public abstract Map<String, Object> reloadBlock(BigInteger blockNumber);

    public abstract Object createRawTransaction(Map<String, Object> param);

    public abstract Object signTransaction(Map<String, Object> param);

    public abstract Object sendTransaction(Map<String, Object> param);

    public abstract Map<String, Object> checkNode();
}
