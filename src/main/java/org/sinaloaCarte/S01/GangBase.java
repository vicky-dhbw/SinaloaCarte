package org.sinaloaCarte.S01;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.sinaloaCarte.utils.NumberToWords;
import org.sinaloaCarte.utils.WordToNumber;

import java.math.BigInteger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GangBase extends Subscriber{

    private String publicKey;
    private final EventBus broadcastEventBus;

    private final Stack<DrugSachet> drugSachets;
    private BigInteger modulus;
    private BigInteger exponent;
    public GangBase(int id, BigInteger modulus, BigInteger exponent) {
        super(id);
        this.modulus = modulus;
        this.exponent = exponent;
        broadcastEventBus=new EventBus();
        drugSachets =new Stack<>();

        for(int i=0;i<15000;i++){
            drugSachets.push(new DrugSachet());
        }
    }
    @Subscribe
    public void receiveRequest(DrugsRequestEvent drugsRequestEvent) throws Exception {
        String requestMessage=drugsRequestEvent.getRequestMessage();
        int gangID= WordToNumber.convert(requestMessage.split("X")[1]);

        System.out.println("BASE -- request received from GANG (id "+gangID+"): "+requestMessage);

        //  broadcasting encrypted request message
        broadcastRequest(encryptRequestMessage(requestMessage, modulus, exponent));

        // sending 100 drug sachets as requested
        broadcastEventBus.post(new ReceiveDrugsEvent(sendDrugs(100),gangID));

        String broadcastDrugsTransactionMessage="DRUGS"+"X"+"ONE"+"HUNDRED"+"X"+"SEND"+"X"+"TO"+"Y"+"LOCATION"+"X"+NumberToWords.convert(gangID);
        broadcastDrugsTransaction(broadcastDrugsTransactionMessage);

    }



    public void broadcastRequest(String broadcastMessage){
        broadcastEventBus.post(new BroadcastEvent(broadcastMessage));
    }

    public void broadcastDrugsTransaction(String broadcastDrugsTransactionMessage){
        broadcastEventBus.post(new BroadcastDrugsTransactionEvent(broadcastDrugsTransactionMessage));
    }

    public String encryptRequestMessage(String requestMessage, BigInteger modulus, BigInteger exponent) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, Exception {

        // Konvertiere die Public-Key-Komponenten in eine PublicKey-Instanz
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // Erzeuge einen Cipher-Objekt und initialisiere es mit dem öffentlichen Schlüssel
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Konvertiere die Nachricht in ein Byte-Array und verschlüssel es
        byte[] requestBytes = requestMessage.getBytes("UTF-8");
        byte[] encryptedBytes = cipher.doFinal(requestBytes);

        return encryptedBytes.toString();
    }

    public List<DrugSachet> sendDrugs(int numberOfSachets){

        List<DrugSachet> requestDrugSachets =new ArrayList<>();

        for(int i=0;i<numberOfSachets;i++){
            if(!drugSachets.isEmpty()){
                requestDrugSachets.add(drugSachets.pop());
            }
            else {
                System.out.println("drugs out of stock :(...");
            }

        }

        return requestDrugSachets;
    }

    public void addSubscriber(Subscriber subscriber){
        broadcastEventBus.register(subscriber);
    }
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }


}
