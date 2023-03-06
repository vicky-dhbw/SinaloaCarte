package org.sinaloaCarte;

import org.sinaloaCarte.S01.GangBase;
import org.sinaloaCarte.S01.GangSite;
import org.sinaloaCarte.utils.NumberToWords;
import org.sinaloaCarte.utils.WordToNumber;

public class Main {
    public static void main(String[] args) {

        GangSite site=new GangSite(1);
        GangBase base=new GangBase(0);

        site.addSubscriber(base);
        base.addSubscriber(site);

        site.requestDrugs(10);

    }
}