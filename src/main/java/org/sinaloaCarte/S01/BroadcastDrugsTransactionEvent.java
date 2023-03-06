package org.sinaloaCarte.S01;

public class BroadcastDrugsTransactionEvent {

    private final String drugsTransactionMessage;
    public BroadcastDrugsTransactionEvent(String drugsTransactionMessage){
        this.drugsTransactionMessage=drugsTransactionMessage;
    }

    public String getDrugsTransactionMessage() {
        return drugsTransactionMessage;
    }
}
