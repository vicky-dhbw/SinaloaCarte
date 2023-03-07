package org.sinaloaCarte.S01_S02;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.sinaloaCarte.utils.NumberToWords;
import org.sinaloaCarte.utils.WordToNumber;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GangBase extends Subscriber{

    private PublicKey publicKey;
    private final EventBus broadcastEventBus;
    private final Stack<DrugSachet> drugSachets;

    public GangBase(int id, PublicKey publicKey) throws NoSuchAlgorithmException {
        super(id);
        this.publicKey = publicKey;

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
        broadcastRequest(encryptRequestMessage(requestMessage));

        // sending 100 drug sachets as requested
        broadcastEventBus.post(new ReceiveDrugsEvent(sendDrugs(100),gangID));

        String broadcastDrugsTransactionMessage="DRUGS"+"X"+"ONE"+"HUNDRED"+"X"+"SEND"+"X"+"TO"+"Y"+"LOCATION"+"X"+NumberToWords.convert(gangID);
        broadcastDrugsTransaction(broadcastDrugsTransactionMessage);

    }



    public void broadcastRequest(byte[] broadcastMessage){
        broadcastEventBus.post(new BroadcastEvent(broadcastMessage));
    }

    public void broadcastDrugsTransaction(String broadcastDrugsTransactionMessage){
        broadcastEventBus.post(new BroadcastDrugsTransactionEvent(broadcastDrugsTransactionMessage));
    }

    public byte[] encryptRequestMessage(String requestMessage) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, Exception, NumberFormatException {

        // get a PublicKey instance
        byte[] messageBytes = requestMessage.getBytes("UTF-8");

        // get an RSA cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());

        // encrypt the message  using the public key
        byte[] encryptedBytes = cipher.doFinal(messageBytes);

        return encryptedBytes;
    }

    public PublicKey getPublicKey() {
        return publicKey;
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



    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

}
