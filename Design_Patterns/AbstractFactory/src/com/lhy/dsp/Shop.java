package com.lhy.dsp;

import com.lhy.dsp.abstractFactory.ClothesFactory;
import com.lhy.dsp.abstractProduct.Clothes;
import com.lhy.dsp.abstractProduct.Trousers;

public class Shop {
    Clothes clothes;
    Trousers trousers;

    public void giveSuit(ClothesFactory factory, int chestSize,
                         int waistSize, int height) {
        clothes = factory.createClothes(chestSize, height);
        trousers = factory.createTrousers(waistSize, height);
        showMessage();
    }

    private void showMessage() {
        System.out.println("<套装信息>");
        System.out.println(clothes.getName() + ":");
        System.out.printf("胸围：%d\t", clothes.getChestSize());
        System.out.printf("身高：%d\n", clothes.getHeight());
        System.out.println(trousers.getName() + ":");
        System.out.printf("腰围：%d\t", trousers.getWaistSize());
        System.out.printf("身高：%d\n", trousers.getHeight());
    }
}
