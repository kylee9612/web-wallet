package com.axia.btc.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.utils.ContextPropagatingThreadFactory;
import org.bitcoinj.wallet.CoinSelection;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class BtcService {

    private final static Logger log = LogManager.getLogger(BtcService.class);
    @Value("${coin.test}")
    private boolean isTest;

    private final double SATOSHI = 10000000.0;

    private NetworkParameters network;

    private WalletAppKit walletAppKit;
    private Context context;

    @PostConstruct
    public void getTestnetParam() {
        if (isTest)
            network = TestNet3Params.get();
        else
            network = MainNetParams.get();
        context = new Context(network);
        walletAppKit = new WalletAppKit(network, Script.ScriptType.P2WPKH,null,new File("."),"peer2-testnet");
        PeerAddress peer = new PeerAddress(network,"192.168.219.101",18333);
        walletAppKit.setPeerNodes(peer);
//        walletAppKit.startAsync();
//        walletAppKit.awaitRunning();
    }

    public Wallet generateWallet() {
        return Wallet.createDeterministic(network, Script.ScriptType.P2WPKH);
    }

    public Wallet getWallet(ECKey key) {
        Wallet wallet = Wallet.createBasic(network);
        wallet.importKey(key);
        return wallet;
    }

    public Map<String, Object> walletFromPrivateKey(ECKey key) {
        Map<String, Object> walletMap = new HashMap<>();
        walletMap.put("private_key", key.getPrivateKeyAsHex());
        walletMap.put("public_key", key.getPublicKeyAsHex());
        String address = Address.fromKey(network, key, Script.ScriptType.P2WPKH).toString();
        walletMap.put("address", address);
        return walletMap;
    }

    public Transaction createRawTransaction(ECKey key, String toAddress, long value) {
        Transaction tx = new Transaction(network);

        Wallet wallet = getWallet(key);
        Address changeAddress = Address.fromKey(network, key, Script.ScriptType.P2WPKH);
        Address destination = Address.fromString(network, toAddress);

        Coin totalAmount = Coin.valueOf(value);
        tx.addOutput(totalAmount, destination);
        Coin changeAmount = Coin.ZERO;
        Coin inputValue = totalAmount;
        if (changeAmount.isGreaterThan(Coin.ZERO)) {
            tx.addOutput(changeAmount, changeAddress);
            inputValue = inputValue.add(changeAmount);
        }

        // Select the UTXOs to spend for the transaction
        List<TransactionOutput> spendableOutputs = wallet.calculateAllSpendCandidates();
        CoinSelection coinSelection = wallet.getCoinSelector().select(Coin.valueOf(inputValue.value), spendableOutputs);
        Collection<TransactionOutput> inputs = coinSelection.gathered;

        // Add the inputs to the transaction
        for (TransactionOutput input : inputs) {
            tx.addInput(input);
        }

        return tx;
    }

    public void signTransaction(Transaction tx, ECKey key) {
        // Sign the inputs
        for (int i = 0; i < tx.getInputs().size(); i++) {
            TransactionInput input = tx.getInput(i);
            ECKey.ECDSASignature signature = key.sign(input.getHash());
            TransactionSignature txSignature = new TransactionSignature(signature, Transaction.SigHash.ALL, false);
            Script inputScript = ScriptBuilder.createInputScript(txSignature, key);
            input.setScriptSig(inputScript);
        }
    }


    public void broadcastTransaction(Transaction tx) throws IOException, BlockStoreException, ExecutionException, InterruptedException {
        // Connect to a Bitcoin node
        BlockChain chain = new BlockChain(network, new MemoryBlockStore(network));
        PeerGroup peerGroup = new PeerGroup(network, chain);
        peerGroup.addAddress(new PeerAddress(network, InetAddress.getLocalHost()));
        peerGroup.start();
        peerGroup.waitForPeers(1).get();

        // Broadcast the transaction to the network
        TransactionBroadcast broadcast = peerGroup.broadcastTransaction(tx);
        log.info("Transaction Sent ::: " + broadcast);
    }

    public BigDecimal getBalance(String adr) {
        Context.propagate(context);
        Address address = Address.fromString(network, adr);
        Wallet wallet = Wallet.createBasic(network);

//        walletAppKit.startAsync();
//        walletAppKit.awaitRunning();
        wallet.addWatchedAddress(address, 0);
        System.out.println("wallet.getWatchedAddresses()" + wallet.getWatchedAddresses());
        return wallet.getBalance().toBtc();
    }

    public BigDecimal getBalance(ECKey key){
        Context.propagate(context);
        Wallet wallet = Wallet.createBasic(network);
        wallet.importKey(key);
        log.info(wallet.toString());
        return wallet.getBalance().toBtc();
    }
}
