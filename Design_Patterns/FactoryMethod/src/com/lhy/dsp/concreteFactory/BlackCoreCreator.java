package com.lhy.dsp.concreteFactory;

import com.lhy.dsp.abstractFactory.CoreCreator;
import com.lhy.dsp.abstractProduct.PenCore;
import com.lhy.dsp.concreteProduct.BlackPenCore;

public class BlackCoreCreator extends CoreCreator {
    @Override
    public PenCore getPenCore() {
        return new BlackPenCore();
    }
}
