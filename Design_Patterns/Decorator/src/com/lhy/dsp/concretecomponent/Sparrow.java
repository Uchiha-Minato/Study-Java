package com.lhy.dsp.concretecomponent;

import com.lhy.dsp.component.Bird;

public class Sparrow extends Bird {

    public static final int DISTANCE = 100;
    @Override
    public int fly() {
        return DISTANCE;
    }
}
