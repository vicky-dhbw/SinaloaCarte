package org.sinaloaCarte.S01_S02;

import com.google.common.eventbus.Subscribe;
import org.sinaloaCarte.S03.RSACracker;
import org.sinaloaCarte.S03.RSACrackerException;

import java.math.BigInteger;
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
        BigInteger e = BigInteger.valueOf(12371);;
        BigInteger n = new BigInteger("517815623413379");
        BigInteger d;
        BigInteger c;
        String requestMassage = "sdsds";
        StringBuilder gang = new StringBuilder();
        int gangID;
        RSACracker rsaCracker = new RSACracker(n,e,new BigInteger(broadcastMessage));
        try {
            BigInteger plainMessage = rsaCracker.execute();
            System.out.println("plainMessage : " + plainMessage);
        } catch (RSACrackerException rsae) {
            System.out.println(rsae.getMessage());
        }
        for(int i = 5; i<requestMassage.length(); i++){
            if(requestMassage.charAt(i) == 'X' && requestMassage.charAt(i+1) == 'S'){
                i +=  18;
               for (int j = i; j<requestMassage.length()-1; j++){
                   gang.append(requestMassage.charAt(j));
               }
               gangID = Integer.valueOf(String.valueOf(gang));
               seizeDrugsFromGang(gangID);
               break;
            }
        }
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
