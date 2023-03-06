package org.sinaloaCarte.S01;

public class BroadcastEvent {

    private final String broadcastMessage;
    public BroadcastEvent(String broadCastMessage){
        this.broadcastMessage=broadCastMessage;
    }

    public String getBroadcastMessage() {
        return broadcastMessage;
    }
}
