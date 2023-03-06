package org.sinaloaCarte.S01;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GangBase extends Subscriber{

    private String publicKey;
    private final EventBus broadcastEventBus;

    private final Stack<DrugBarrel> drugBarrels;

    public GangBase(int id) {
        super(id);
        broadcastEventBus=new EventBus();
        drugBarrels=new Stack<>();
        for(int i=0;i<20;i++){
            drugBarrels.push(new DrugBarrel());
        }
    }
    @Subscribe
    public void receiveRequest(DrugsRequestEvent drugsRequestEvent){

        String requestMessage=drugsRequestEvent.getRequestMessage();
        System.out.println("request received: "+requestMessage);

        broadcast(encryptRequestMessage(requestMessage));
        broadcastEventBus.post(new ReceiveDrugsEvent(sendDrugs(drugsRequestEvent.getNumberOfDrugBarrels())));

    }



    public void broadcast(String broadcastMessage){
        broadcastEventBus.post(new BroadcastEvent(broadcastMessage));
    }

    public String encryptRequestMessage(String requestMessage){
        /*

        encryption of request message goes here

         */
        return "encryptedMessage";
    }

    public List<DrugBarrel> sendDrugs(int numberOfBarrels){

        List<DrugBarrel> requestDrugBarrels=new ArrayList<>();

        for(int i=0;i<numberOfBarrels;i++){
            if(!drugBarrels.isEmpty()){
                requestDrugBarrels.add(drugBarrels.pop());
            }
            else {
                System.out.println("drugs out of stock :(...");
            }

        }

        return requestDrugBarrels;
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
