package com.lhy.dsp.concreteFactory;

import com.lhy.dsp.abstractFactory.ClothesFactory;
import com.lhy.dsp.abstractProduct.Clothes;
import com.lhy.dsp.abstractProduct.Trousers;
import com.lhy.dsp.concreteProduct.CowboyClothes;
import com.lhy.dsp.concreteProduct.CowboyTrousers;

public class ShanghaiFactory extends ClothesFactory {
    @Override
    public Clothes createClothes(int chestSize, int height) {
        return new CowboyClothes("上海牌牛仔上衣", chestSize, height);
    }

    @Override
    public Trousers createTrousers(int waistSize, int height) {
        return new CowboyTrousers("上海牌牛仔裤", waistSize, height);
    }
}
