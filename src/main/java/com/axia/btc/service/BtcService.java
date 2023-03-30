package com.axia.btc.service;

import org.bitcoinj.core.*;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.CoinSelection;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Objects;

@Service
public class BtcService {
    @Value("${coin.test}")
    private boolean isTest;

    private NetworkParameters network;

    @PostConstruct
    public void getTestnetParam(){
        if(isTest)
            network = MainNetParams.fromID(MainNetParams.ID_TESTNET);
        else
            network = MainNetParams.fromID(MainNetParams.ID_MAINNET);
    }

    public Wallet generateWallet(){
        return Wallet.createDeterministic(network, Script.ScriptType.P2WPKH);
    }

    public Transaction createRawTransaction(Wallet wallet, Address destination, long value, Address changeAddress) throws InsufficientMoneyException {
        // Create a new empty transaction
        Transaction tx = new Transaction(network);
        ECKey key = ECKey.fromPrivate(Objects.requireNonNull(wallet.getActiveKeyChain().getRootKey()).getPrivKey());

        // Calculate the total amount of coins to be sent
        Coin totalAmount = Coin.valueOf(value);

        // Add the output to send coins to the destination address
        tx.addOutput(totalAmount, destination);

        // Calculate the amount of change to be returned to the change address
        Coin changeAmount = Coin.ZERO;

        // Calculate the total value of inputs required for the transaction
        Coin inputValue = totalAmount;

        // If there is change to be returned, add an output to the change address
        if (changeAmount.isGreaterThan(Coin.ZERO)) {
            tx.addOutput(changeAmount, changeAddress);
            inputValue = inputValue.add(changeAmount);
        }

        // Select the UTXOs to spend for the transaction
        List<TransactionOutput> spendableOutputs = wallet.calculateAllSpendCandidates();
        CoinSelection coinSelection = wallet.getCoinSelector().select(Coin.valueOf(inputValue.value), spendableOutputs);
        List<TransactionInput> inputs = coinSelection.gathered;

        // Add the inputs to the transaction
        for (TransactionInput input : inputs) {
            tx.addInput(input);
        }

        // Sign the inputs
        for (int i = 0; i < inputs.size(); i++) {
            TransactionInput input = tx.getInput(i);
            Script scriptPubKey = input.getConnectedOutput().getScriptPubKey();
            ECKey.ECDSASignature signature = key.sign(input.getConnectedOutput().getValue(), key.getPrivKeyBytes(), true);
            TransactionSignature txSignature = new TransactionSignature(signature, Transaction.SigHash.ALL, false);
            Script inputScript = ScriptBuilder.createInputScript(txSignature, key);
            input.setScriptSig(inputScript);
        }

        return tx;
    }

    public void signTransaction(NetworkParameters params, Transaction tx, ECKey key) {
        // Sign the inputs
        for (int i = 0; i < tx.getInputs().size(); i++) {
            TransactionInput input = tx.getInput(i);
            Script scriptPubKey = input.getConnectedOutput().getScriptPubKey();
            ECKey.ECDSASignature signature = key.sign(input.getConnectedOutput().getValue(), key.getPrivKeyBytes(), true);
            TransactionSignature txSignature = new TransactionSignature(signature, Transaction.SigHash.ALL, false);
            Script inputScript = ScriptBuilder.createInputScript(txSignature, key);
            input.setScriptSig(inputScript);
        }
    }



    public void broadcastTransaction( Transaction tx) throws IOException, BlockStoreException {
        // Connect to a Bitcoin node
        BlockChain chain = new BlockChain(network, new MemoryBlockStore(network));
        PeerGroup peerGroup = new PeerGroup(network, chain);
        peerGroup.addAddress(new PeerAddress(network,InetAddress.getLocalHost()));
        peerGroup.start();
        peerGroup.waitForPeers(1).get();

        // Broadcast the transaction to the network
        peerGroup.broadcastTransaction(tx).get();
    }
}
