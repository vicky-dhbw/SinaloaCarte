package org.sinaloaCarte.S01_S02;

public class DrugsRequestEvent {

    private final String requestMessage;

    public DrugsRequestEvent(String requestMessage){
        this.requestMessage=requestMessage;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

}
