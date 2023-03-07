package org.sinaloaCarte.S04;

public class Application {
    public void encryptAndDecrypt(){
        String plainMessage = "LOCATIONXONEXREQUESTXONEHUNDREDX";
        StringBuilder keyword = new StringBuilder("DHBWMGH");

        Engine engine = new Engine();

        String key = engine.generateKey(plainMessage, keyword);
        System.out.println("key              : " + key);
        System.out.println();

        System.out.println("plainMessage     : " + plainMessage);
        System.out.println("cipher           : " + engine.encrypt(plainMessage, key));
        System.out.println("decryptedMessage : " + engine.decrypt(engine.encrypt(plainMessage, key), key));
    }
    public void crack(String cipher){
        EngineCracker engine = new EngineCracker();
        long runtimeStart;
        System.out.println("--- 1st message ---");
        runtimeStart = System.currentTimeMillis();
        engine.execute("LOCATIONXONEXREQUESTXONEHUNDREDX", 7, 8);
        System.out.println("runtime : " + (System.currentTimeMillis() - runtimeStart) + " ms\n");
    }
}
