package org.sinaloaCarte.S01_S02;

public class BroadcastDrugsTransactionEvent {

    private final String drugsTransactionMessage;
    public BroadcastDrugsTransactionEvent(String drugsTransactionMessage){
        this.drugsTransactionMessage=drugsTransactionMessage;
    }

    public String getDrugsTransactionMessage() {
        return drugsTransactionMessage;
    }
}
