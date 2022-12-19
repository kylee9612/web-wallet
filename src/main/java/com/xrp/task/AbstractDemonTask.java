package com.xrp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractDemonTask
 *
 * @author ohs
 * @version 1.0
 */
public abstract class AbstractDemonTask extends Thread{
    //	protected Log log = LogFactory.getLog(AbstractDemonTask.class);
    private static Logger log = LoggerFactory.getLogger(AbstractDemonTask.class);

    protected volatile boolean isRunning = true;
    protected volatile boolean isPaused = false;
    protected final Object pauseLock = new Object();

    protected long sleepTime = 1000 * 5;
    protected String coinType = null;
    protected int RETRY_COUNT = 5;

    protected Map<String, Object> coinMap;
    protected String withdraw_eth_fee_wallet;

    protected abstract void execute() throws Exception;
    protected abstract void openDBNode();
    protected abstract void closeDBNode();

    public void startThread() throws Exception {
    }

    public boolean getProcessStatus() {
        return !isPaused;
    }

    public void stopThread() throws Exception {
        stopProcess();
        interrupt();
        isRunning = false;
        synchronized (pauseLock) {
            isPaused = false;
            pauseLock.notifyAll(); // Unblocks thread
        }
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void startProcess() throws Exception{
        log.debug(" ============ Task (startProcess) ============ ");
        openDBNode ();
        synchronized (pauseLock) {
            isPaused = false;
            pauseLock.notifyAll(); // Unblocks thread
        }
    }

    public void stopProcess(){
        log.debug(" ============ Task (stopProcess) ============ ");
        isPaused = true;
        closeDBNode();
    }

    @Override
    public void run() {
        int reTryCount = 0;
        while(isRunning) {
            synchronized (pauseLock) {
                if (!isRunning) { // may have changed while waiting to
                    // synchronize on pauseLock
                    break;
                }
                if (isPaused) {
                    try {
                        pauseLock.wait(); // will cause this Thread to block until
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!isRunning) { // running might have changed since we paused
                        break;
                    }
                }
            }

            try {
                // 네트웍이 문제가 생기면 kill
                if (reTryCount > RETRY_COUNT) {
                    stopThread();
                }
                // 기다렸다가 다시 재개 되었을겨우에는 reTryCount를 다시 0으로 복귀
                else {
                    // 왜 내가 여기서 계속 살렸다 죽였다 했지??? 의문...
//					openDBNode ();
                    execute();
//					closeDBNode();
                    Thread.sleep(sleepTime);
                    reTryCount = 0;
                }
            } catch(Exception e) {
                e.printStackTrace();
                log.error("exception :: " + e);
                reTryCount++;
                try {
                    closeDBNode();
                    Thread.sleep(60000 * 5); // 로직상 문제가 없고 네트웍 또는 노드에서 문제가 생기면 1분뒤에 다시 reTry해보자
                    openDBNode();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    log.error("exception :: " + e1);
                }
            }
        }
    }

}
