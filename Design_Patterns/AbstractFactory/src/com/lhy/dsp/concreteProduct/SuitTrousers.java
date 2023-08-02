package com.lhy.dsp.concreteProduct;

import com.lhy.dsp.abstractProduct.Trousers;

public class SuitTrousers extends Trousers {

    public SuitTrousers(String name, int waistSize, int height) {
        this.name = name;
        this.waistSize = waistSize;
        this.height = height;
    }
    @Override
    public int getWaistSize() {
        return waistSize;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getName() {
        return name;
    }
}
