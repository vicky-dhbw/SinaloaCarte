package org.sinaloaCarte.S01;

import java.util.List;

public class ReceiveDrugsEvent {
    private final List<DrugSachet> drugSachets;
    private final int gangID;

    public ReceiveDrugsEvent(List<DrugSachet> drugSachets,int gangID){
        this.drugSachets = drugSachets;
        this.gangID=gangID;
    }

    public List<DrugSachet> getDrugSachets() {
        return drugSachets;
    }

    public int getGangID() {
        return gangID;
    }
}
