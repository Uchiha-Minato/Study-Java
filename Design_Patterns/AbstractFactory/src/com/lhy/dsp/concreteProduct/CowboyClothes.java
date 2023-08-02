package com.lhy.dsp.concreteProduct;

import com.lhy.dsp.abstractProduct.Clothes;

public class CowboyClothes extends Clothes {

    public CowboyClothes(String name, int height, int chestSize) {
        this.name = name;
        this.height = height;
        this.chestSize = chestSize;
    }
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getChestSize() {
        return chestSize;
    }
}
