package com.lhy.dsp.decorator;

import com.lhy.dsp.component.Bird;

public abstract class Decorator extends Bird {
    protected Bird bird; //保存的被装饰者的引用。

    public Decorator() {
    }

    public Decorator(Bird bird) {
        this.bird = bird;
    }
}
