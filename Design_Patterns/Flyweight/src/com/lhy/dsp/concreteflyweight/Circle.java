package com.lhy.dsp.concreteflyweight;

import com.lhy.dsp.flyweight.CircleShape;

public class Circle extends CircleShape {

    public Circle(String color) {
        super(color);
    }

    @Override
    public void draw() {
        System.out.print("Circle: draw()");
        System.out.println("[Color : " + color +", x : " + x +", y :" + y +", radius :" + radius);
    }
}
