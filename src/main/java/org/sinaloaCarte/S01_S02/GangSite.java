package org.sinaloaCarte.S01_S02;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.sinaloaCarte.utils.NumberToWords;
import org.sinaloaCarte.utils.WordToNumber;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class GangSite extends Subscriber {

    private PrivateKey privateKey;
    private final EventBus requestEventBus;
    private final List<DrugSachet> receivedDrugSachets;
    private final List<String> broadcastRequestsProtocols;
    private final List<String> broadcastDrugsTransactionsProtocols;

    public GangSite(int id, PrivateKey privateKey) throws NoSuchAlgorithmException {
        super(id);
        this.privateKey = privateKey;
        requestEventBus = new EventBus();
        receivedDrugSachets = new ArrayList<>();
        broadcastRequestsProtocols = new ArrayList<>();
        broadcastDrugsTransactionsProtocols = new ArrayList<>();
    }

    @Subscribe
    public void receiveBroadcast(BroadcastEvent broadcastEvent) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException, InvalidKeyException {
        byte[] broadcastMessage = broadcastEvent.getBroadcastMessage();
        System.out.println("GANG (id " + id + ") received broadcast message -> " + broadcastMessage + " <- from gang base....");

        decryptBroadcastMessage(broadcastMessage);
    }

    public void requestDrugs() {
        // always requests 100 drug sachets
        String requestMessage = "LOCATION" + "X" + NumberToWords.convert(id) + "X" + "REQUEST" + "X" + "ONE" + "HUNDRED" + "X";
        requestEventBus.post(new DrugsRequestEvent(requestMessage));
    }

    public void addSubscriber(Subscriber subscriber) {
        requestEventBus.register(subscriber);
    }

    @Subscribe
    public void receiveDrugSachets(ReceiveDrugsEvent receiveDrugsEvent) {
        // drugs will be received if the gang ID matches
        if (receiveDrugsEvent.getGangID() == id) {
            receivedDrugSachets.addAll(receiveDrugsEvent.getDrugSachets());
            System.out.println("GANG (id " + id + ") received " + receiveDrugsEvent.getDrugSachets().size() + " drug sachets");
        }
    }

    public void decryptBroadcastMessage(byte[] broadcastMessage) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, NumberFormatException {

        //improper decryption leads to index out of bound array

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());

        //decrypt the text using the private key
        byte[] decryptedBytes = cipher.doFinal(broadcastMessage);

        //Convert the decrypted bytes to a string
        String decryptedBroadcastMessage = new String(decryptedBytes, "UTF-8");
        System.out.println("GANG (id " + id + ") decrypted broadcast message -> " + decryptedBroadcastMessage + " <- from gang base....");
        protocolBroadcastMessage(broadcastMessage, decryptedBroadcastMessage);
    }


    public PrivateKey getPrivateKey() {
        return privateKey;
    }


    public void protocolBroadcastMessage(byte[] broadcastMessage, String decryptedBroadcastMessage) {
        int location = WordToNumber.convert(decryptedBroadcastMessage.split("X")[1]);
        long currentTimeInNanos = System.nanoTime();
        broadcastRequestsProtocols.add(currentTimeInNanos + " | Location [Base " + location + "] | " + broadcastMessage + " | " + decryptedBroadcastMessage);

    }

    @Subscribe
    public void receiveDrugsTransactionMessage(BroadcastDrugsTransactionEvent broadcastDrugsTransactionEvent) {
        broadcastDrugsTransactionsProtocols.add(broadcastDrugsTransactionEvent.getDrugsTransactionMessage());
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public List<DrugSachet> getReceivedDrugSachets() {
        return receivedDrugSachets;
    }

    public List<String> getBroadcastProtocols() {
        return broadcastRequestsProtocols;
    }

    public List<String> getBroadcastDrugsTransactionsProtocols() {
        return broadcastDrugsTransactionsProtocols;
    }
}
