package org.sinaloaCarte.S01;

import org.sinaloaCarte.utils.WordToNumber;

public class DrugsRequestEvent {

    private final String requestMessage;

    private final int numberOfDrugBarrels;
    public DrugsRequestEvent(String requestMessage){
        this.requestMessage=requestMessage;
        numberOfDrugBarrels= WordToNumber.convert(requestMessage.split("X")[1]);
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public int getNumberOfDrugBarrels() {
        return numberOfDrugBarrels;
    }
}
