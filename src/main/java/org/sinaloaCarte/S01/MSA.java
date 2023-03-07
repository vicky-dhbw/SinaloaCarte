package org.sinaloaCarte.S01;

import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MSA extends Subscriber{
    private final List<GangSite> gangSites;
    private final List<DrugSachet> seizedDrugs;
    public MSA(int id) {
        super(id);
        this.gangSites = new ArrayList<>();
        this.seizedDrugs = new ArrayList<>();
    }


    @Subscribe
    public void receiveBroadcast(BroadcastEvent broadcastEvent){
        String broadcastMessage=broadcastEvent.getBroadcastMessage().toString();
        System.out.println("MSA gets encrypted message: "+broadcastMessage);
        decryptBroadcastMessage(broadcastMessage);
    }

    public void decryptBroadcastMessage(String broadcastMessage){
        /*
        Cryptographic analysis here

        find out gangID

        */
    }

    public void seizeDrugsFromGang(int gangID){
        System.out.println("SEIZING DRUGS FROM GANG (ID"+gangID+")");
        for(GangSite gangSite:gangSites){
            if(gangSite.getID()==gangID){
                seizedDrugs.addAll(gangSite.getReceivedDrugSachets());
            }
        }
    }

    public List<GangSite> getGangSites() {
        return gangSites;
    }

    public List<DrugSachet> getSeizedDrugs() {
        return seizedDrugs;
    }
}
