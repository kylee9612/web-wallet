package com.axia.btc.util;

import com.axia.common.util.RPCClient;
import com.axia.model.vo.NodeConfig;

public class BtcUtil{
    private RPCClient rpcClient;

    public BtcUtil(NodeConfig nodeConfig){
        rpcClient = new RPCClient(nodeConfig);
    }
}
