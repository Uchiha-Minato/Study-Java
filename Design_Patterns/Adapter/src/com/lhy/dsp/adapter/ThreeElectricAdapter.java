package com.lhy.dsp.adapter;

import com.lhy.dsp.adaptee.TwoElectricOutlet;
import com.lhy.dsp.target.ThreeElectricOutlet;

public class ThreeElectricAdapter implements ThreeElectricOutlet {
    TwoElectricOutlet outlet2;

    public ThreeElectricAdapter(TwoElectricOutlet outlet2) {
        this.outlet2 = outlet2;
    }

    @Override
    public void connectElectricCurrent() {
        outlet2.connectElectricCurrent();
    }
}
