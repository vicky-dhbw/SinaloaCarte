package org.sinaloaCarte.S01;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.sinaloaCarte.utils.NumberToWords;
import org.sinaloaCarte.utils.WordToNumber;

import java.util.ArrayList;
import java.util.List;

public class GangSite extends Subscriber{

    private String privateKey;
    private final EventBus requestEventBus;
    private final List<DrugSachet> receivedDrugSachets;
    private final List<String> broadcastRequestsProtocols;
    private final List<String> broadcastDrugsTransactionsProtocols;
    public GangSite(int id) {
        super(id);
        requestEventBus=new EventBus();
        receivedDrugSachets =new ArrayList<>();
        broadcastRequestsProtocols =new ArrayList<>();
        broadcastDrugsTransactionsProtocols=new ArrayList<>();
    }
    @Subscribe
    public void receiveBroadcast(BroadcastEvent broadcastEvent){
        String broadcastMessage=broadcastEvent.getBroadcastMessage();
        System.out.println("GANG (id "+id+") received broadcast message -> "+broadcastMessage+" <- from gang base....");

        decryptBroadcastMessage(broadcastMessage);
    }

    public void requestDrugs(){
        // always requests 100 drug sachets
        String requestMessage="LOCATION"+"X" +NumberToWords.convert(id)+"X"+"REQUEST"+"X"+"ONE"+"HUNDRED"+"X";
        requestEventBus.post(new DrugsRequestEvent(requestMessage));
    }
    public void addSubscriber(Subscriber subscriber){
        requestEventBus.register(subscriber);
    }

    @Subscribe
    public void receiveDrugSachets(ReceiveDrugsEvent receiveDrugsEvent){
        // drugs will be received if the gang ID matches
        if(receiveDrugsEvent.getGangID()==id){
            receivedDrugSachets.addAll(receiveDrugsEvent.getDrugSachets());
            System.out.println("GANG (id "+id+") received "+receiveDrugsEvent.getDrugSachets().size()+" drug sachets");
        }
    }

    public void decryptBroadcastMessage(String broadcastMessage){

        String decryptedBroadcastMessage="LOCATIONXONEXREQUESTXONEHUNDREDX"; // <---- this is an example, decrypt the broadcast message and set it to this variable
        // improper decryption leads to index out of bound array
        /*

        Decryption here

         */

        // protocol drug transaction

        protocolBroadcastMessage(broadcastMessage,decryptedBroadcastMessage);
    }

    public void protocolBroadcastMessage(String broadcastMessage,String decryptedBroadcastMessage){
        int location= WordToNumber.convert(decryptedBroadcastMessage.split("X")[1]);
        long currentTimeInNanos = System.nanoTime();
        broadcastRequestsProtocols.add(currentTimeInNanos+" | Location [Base "+location+"] | "+broadcastMessage+" | "+decryptedBroadcastMessage);

    }
    public String getPrivateKey() {
        return privateKey;
    }

    @Subscribe
    public void receiveDrugsTransactionMessage(BroadcastDrugsTransactionEvent broadcastDrugsTransactionEvent){
        broadcastDrugsTransactionsProtocols.add(broadcastDrugsTransactionEvent.getDrugsTransactionMessage());
    }

    public void setPrivateKey(String privateKey) {
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
