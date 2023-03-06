package org.sinaloaCarte;

import org.sinaloaCarte.S01.GangBase;
import org.sinaloaCarte.S01.GangSite;
import org.sinaloaCarte.S01.MSA;

public class Main {
    public static void main(String[] args) {

        GangSite site=new GangSite(1);
        GangBase base=new GangBase(0);
        GangSite site2=new GangSite(2);
        MSA msa=new MSA(1000);

        msa.getGangSites().add(site);

        site.addSubscriber(base);
        site2.addSubscriber(base);
        base.addSubscriber(site);
        base.addSubscriber(site2);
        base.addSubscriber(msa);

        site.requestDrugs();  // requests 100 drug sachets
        site2.requestDrugs();

        for(String protocol:site.getBroadcastProtocols()){
            System.out.println(protocol);
        }

        for(String transaction:site.getBroadcastDrugsTransactionsProtocols()){
            System.out.println(transaction);
        }


        msa.seizeDrugsFromGang(1);
    }
}