package org.sinaloaCarte;

import org.sinaloaCarte.S01_S02.GangBase;
import org.sinaloaCarte.S01_S02.GangSite;
import org.sinaloaCarte.S01_S02.MSA;
import java.security.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        KeyPair keyPair;
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Key with 2048 Bits
        keyPair = keyGen.generateKeyPair();

        GangBase base=new GangBase(0, (PublicKey) keyPair.getPublic());

        GangSite[] sites=new GangSite[20];
        for (int i = 0; i < 20; i++) {
            sites[i] = new GangSite(i+1, (PrivateKey) keyPair.getPrivate());
        }



        MSA msa=new MSA(1000);

        msa.getGangSites().add(sites[1]);

        for (int i = 0; i < 20; i++) {
            sites[i].addSubscriber(base);
            base.addSubscriber(sites[i]);
        }

        base.addSubscriber(msa);



            sites[0].requestDrugs();
            sites[1].requestDrugs();
            sites[2].requestDrugs();
            sites[3].requestDrugs();
            sites[4].requestDrugs();
            sites[5].requestDrugs();
            sites[6].requestDrugs();
            sites[7].requestDrugs();
            sites[8].requestDrugs();
            sites[9].requestDrugs();
            sites[10].requestDrugs();
            sites[11].requestDrugs();
            sites[12].requestDrugs();
            sites[13].requestDrugs();
            sites[14].requestDrugs();
            sites[15].requestDrugs();
            sites[16].requestDrugs();
            sites[17].requestDrugs();
            sites[18].requestDrugs();
            sites[19].requestDrugs();





         // requests 100 drug sachets
        for (int i = 0; i < 20; i++) {
            for (String protocol : sites[i].getBroadcastProtocols()) {
                System.out.println(protocol);
            }

            for (String transaction : sites[i].getBroadcastDrugsTransactionsProtocols()) {
                System.out.println(transaction);
            }
        }

       msa.seizeDrugsFromGang(1);
    }
}