package com.lhy.dsp;

import com.lhy.dsp.abstractProduct.PenCore;

/**
 * 圆珠笔类
 * */
public class BallPen {
    PenCore core;

    public void usePenCore(PenCore core) {
        this.core = core;
    }

    public void write(String s) {
        core.writeWord(s);
    }
}
