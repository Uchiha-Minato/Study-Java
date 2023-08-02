package com.lhy.dsp.abstractFactory;

import com.lhy.dsp.abstractProduct.Clothes;
import com.lhy.dsp.abstractProduct.Trousers;

public abstract class ClothesFactory {
    public abstract Clothes createClothes(int chestSize, int height);
    public abstract Trousers createTrousers(int waistSize, int height);
}
