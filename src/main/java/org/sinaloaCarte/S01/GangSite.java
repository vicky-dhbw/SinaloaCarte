package org.sinaloaCarte.S01;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.sinaloaCarte.utils.NumberToWords;

import java.util.ArrayList;
import java.util.List;

public class GangSite extends Subscriber{

    private String privateKey;
    private final EventBus requestEventBus;

    private List<DrugBarrel> receivedDrugBarrels;
    public GangSite(int id) {
        super(id);
        requestEventBus=new EventBus();
        receivedDrugBarrels=new ArrayList<>();
    }

    @Subscribe
    public void receiveBroadcast(BroadcastEvent broadcastEvent){

        String broadcastMessage=broadcastEvent.getBroadcastMessage();
        System.out.println("received broadcast message: "+broadcastMessage);

    }

    public void requestDrugs(int numberOfTons){
        String requestMessage="LOCATION"+"X" +NumberToWords.convert(numberOfTons)+"X"+"REQUEST"+"X"+"ONE"+"HUNDRED"+"X";
        requestEventBus.post(new DrugsRequestEvent(requestMessage));
    }
    public void addSubscriber(Subscriber subscriber){
        requestEventBus.register(subscriber);
    }

    @Subscribe
    public void receiveDrugBarrels(ReceiveDrugsEvent receiveDrugsEvent){
        receivedDrugBarrels.addAll(receiveDrugsEvent.getDrugBarrels());
        System.out.println("received barrels: "+receiveDrugsEvent.getDrugBarrels().size());
    }

    public void decryptBroadcastMessage(String broadcastMessage){

        /*

        Decryption here
         */
    }
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
