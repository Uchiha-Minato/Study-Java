package com.lhy.dsp.concretedecorator;

import com.lhy.dsp.component.Bird;
import com.lhy.dsp.decorator.Decorator;

public class ElectricWings extends Decorator {

    private static final int DISTANCE = 50;

    public ElectricWings(Bird bird) {
        super(bird);
    }

    @Override
    public int fly() {
        int distance;
        distance = bird.fly() + eleFly();
        //委托被装饰者bird调用fly(),然后再调用eleFly()
        return distance;
    }

    private int eleFly() {
        return DISTANCE;
    }
}
