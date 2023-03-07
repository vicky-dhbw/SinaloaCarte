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
            sites[i].requestDrugs();
        }

        base.addSubscriber(msa);



        for (int i = 0; i < 20; i++) {
            sites[i].requestDrugs();
        }




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