package org.sinaloaCarte.S01_S02;

public class BroadcastEvent {

    private final byte[] broadcastMessage;
    public BroadcastEvent(byte[] broadCastMessage){
        this.broadcastMessage=broadCastMessage;
    }

    public byte[] getBroadcastMessage() {
        return broadcastMessage;
    }
}
