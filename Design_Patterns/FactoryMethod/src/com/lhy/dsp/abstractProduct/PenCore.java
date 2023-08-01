package com.lhy.dsp.abstractProduct;

public abstract class PenCore {
    /**
     * 圆珠笔的颜色
     * */
    protected String color;

    /**
     * 用圆珠笔写字
     * */
    public abstract void writeWord(String s);
}
