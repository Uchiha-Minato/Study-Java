package com.lhy.dsp.concreteProduct;

import com.lhy.dsp.abstractProduct.Clothes;

public class SuitClothes extends Clothes {

    public SuitClothes(String name, int height, int chestSize) {
        this.name = name;
        this.height = height;
        this.chestSize = chestSize;
    }
    @Override
    public int getChestSize() {
        return chestSize;
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
