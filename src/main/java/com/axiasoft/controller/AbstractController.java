package com.axiasoft.controller;

import com.axiasoft.task.AbstractDemonTask;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {

    String coinType;

    AbstractDemonTask transactionTask;
    AbstractDemonTask sendTask;
    AbstractDemonTask receiveTask;
    AbstractDemonTask sendToAdminTask;
    AbstractDemonTask sendQueueTask;
    AbstractDemonTask blockSyncTask;

    public abstract void init() throws Exception;

    public void startThread() throws Exception {
        if (transactionTask != null) transactionTask.startThread();
        if (sendTask != null) sendTask.startThread();
        if (receiveTask != null) receiveTask.startThread();
        if (sendToAdminTask != null) sendToAdminTask.startThread();
        if (sendQueueTask != null) sendQueueTask.startThread();
        if (blockSyncTask != null) blockSyncTask.startThread();
    }

    public void stopThread() throws Exception{
        if (transactionTask != null) transactionTask.stopThread();
        if (sendTask != null) sendTask.stopThread();
        if (receiveTask != null) receiveTask.stopThread();
        if (sendToAdminTask != null) sendToAdminTask.stopThread();
        if (sendQueueTask != null) sendQueueTask.stopThread();
        if (blockSyncTask != null) blockSyncTask.stopThread();
    }

    public void startProcess() throws Exception {
        if (transactionTask != null) transactionTask.startProcess();
        if (sendTask != null) sendTask.startProcess();
        if (receiveTask != null) receiveTask.startProcess();
        if (sendToAdminTask != null) sendToAdminTask.startProcess();
        if (sendQueueTask != null) sendQueueTask.startProcess();
        if (blockSyncTask != null) blockSyncTask.startProcess();
    }

    public void stopProcess() throws Exception{
        if (transactionTask != null) transactionTask.stopProcess();
        if (sendTask != null) sendTask.stopProcess();
        if (receiveTask != null) receiveTask.stopProcess();
        if (sendToAdminTask != null) sendToAdminTask.stopProcess();
        if (sendQueueTask != null) sendQueueTask.stopProcess();
        if (blockSyncTask != null) blockSyncTask.stopProcess();
    }

    public Map<String, Boolean> getProcessStatus(){
        Map<String, Boolean> mapResult = new HashMap<String, Boolean>();
        if (transactionTask != null) {
            mapResult.put("txn_daemon_running", transactionTask.getProcessStatus());
        }
        if (sendTask != null) {
            mapResult.put("send_daemon_running", sendTask.getProcessStatus());
        }
        if (receiveTask != null) {
            mapResult.put("receive_daemon_running", receiveTask.getProcessStatus());
        }
        return mapResult;
    }
}
