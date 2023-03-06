package org.sinaloaCarte.S01;

import org.sinaloaCarte.utils.WordToNumber;

public class DrugsRequestEvent {

    private final String requestMessage;

    public DrugsRequestEvent(String requestMessage){
        this.requestMessage=requestMessage;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

}
