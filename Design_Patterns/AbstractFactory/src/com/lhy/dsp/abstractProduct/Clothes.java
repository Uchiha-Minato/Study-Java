package com.lhy.dsp.abstractProduct;

public abstract class Clothes {
    protected int chestSize;
    protected int height;
    protected String name;
    /**
     * 获得胸围
     * */
    public abstract int getChestSize();
    /**
     * 获得身高
     * */
    public abstract int getHeight();
    /**
     * 获得名字
     * */
    public abstract String getName();
}
