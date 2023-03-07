package org.sinaloaCarte;

import org.sinaloaCarte.S01.GangBase;
import org.sinaloaCarte.S01.GangSite;
import org.sinaloaCarte.S01.MSA;
import java.math.BigInteger;
import java.security.*;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        BigInteger modulus = new BigInteger("1234567890abcdef", 16);
//        BigInteger exponent = new BigInteger("010001", 16);
        KeyPair keyPair;
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Verwende einen RSA-Schlüssel mit 2048 Bits
        keyPair = keyGen.generateKeyPair();


        GangSite site=new GangSite(1, (PrivateKey) keyPair.getPrivate());
        GangBase base=new GangBase(0, (PublicKey) keyPair.getPublic());
        GangSite site2=new GangSite(2, (PrivateKey) keyPair.getPrivate());
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


       // msa.seizeDrugsFromGang(1);
    }
}