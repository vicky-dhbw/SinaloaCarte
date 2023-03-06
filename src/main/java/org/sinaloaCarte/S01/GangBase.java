package org.sinaloaCarte.S01;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.sinaloaCarte.utils.NumberToWords;
import org.sinaloaCarte.utils.WordToNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GangBase extends Subscriber{

    private String publicKey;
    private final EventBus broadcastEventBus;

    private final Stack<DrugSachet> drugSachets;

    public GangBase(int id) {
        super(id);
        broadcastEventBus=new EventBus();
        drugSachets =new Stack<>();

        for(int i=0;i<15000;i++){
            drugSachets.push(new DrugSachet());
        }
    }
    @Subscribe
    public void receiveRequest(DrugsRequestEvent drugsRequestEvent){
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



    public void broadcastRequest(String broadcastMessage){
        broadcastEventBus.post(new BroadcastEvent(broadcastMessage));
    }

    public void broadcastDrugsTransaction(String broadcastDrugsTransactionMessage){
        broadcastEventBus.post(new BroadcastDrugsTransactionEvent(broadcastDrugsTransactionMessage));
    }

    public String encryptRequestMessage(String requestMessage){

        /*

        encryption of request message goes here

         */
        return "encryptedMessage";
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
