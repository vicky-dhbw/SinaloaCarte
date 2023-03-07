package org.sinaloaCarte.S01;

public class BroadcastEvent {

    private final byte[] broadcastMessage;
    public BroadcastEvent(byte[] broadCastMessage){
        this.broadcastMessage=broadCastMessage;
    }

    public String getBroadcastMessage() {
        return broadcastMessage;
    }
}
