package com.lhy.dsp.concreteFactory;

import com.lhy.dsp.abstractFactory.ClothesFactory;
import com.lhy.dsp.abstractProduct.Clothes;
import com.lhy.dsp.abstractProduct.Trousers;
import com.lhy.dsp.concreteProduct.SuitClothes;
import com.lhy.dsp.concreteProduct.SuitTrousers;

public class BeijingFactory extends ClothesFactory {
    @Override
    public Clothes createClothes(int chestSize, int height) {
        return new SuitClothes("北京牌西服上衣", chestSize, height);
    }

    @Override
    public Trousers createTrousers(int waistSize, int height) {
        return new SuitTrousers("北京牌西服裤子", waistSize, height);
    }
}
