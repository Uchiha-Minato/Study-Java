package com.lhy.dsp;

import com.lhy.dsp.abstractFactory.ClothesFactory;
import com.lhy.dsp.concreteFactory.BeijingFactory;
import com.lhy.dsp.concreteFactory.ShanghaiFactory;

public class Application_AbsFac {
    public static void main(String[] args) {
        Shop shop = new Shop();
        ClothesFactory factory = new BeijingFactory();
        shop.giveSuit(factory, 110, 82, 170);
        ClothesFactory factory1 = new ShanghaiFactory();
        shop.giveSuit(factory1, 120, 88, 180);
    }
}
