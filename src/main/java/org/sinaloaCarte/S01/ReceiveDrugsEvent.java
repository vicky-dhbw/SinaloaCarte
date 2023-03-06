package org.sinaloaCarte.S01;

import java.util.List;

public class ReceiveDrugsEvent {
    private final List<DrugBarrel> drugBarrels;
    public ReceiveDrugsEvent(List<DrugBarrel> drugBarrels){
        this.drugBarrels=drugBarrels;
    }

    public List<DrugBarrel> getDrugBarrels() {
        return drugBarrels;
    }
}
