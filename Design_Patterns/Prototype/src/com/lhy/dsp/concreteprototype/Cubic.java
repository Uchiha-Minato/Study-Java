package com.lhy.dsp.concreteprototype;

import com.lhy.dsp.prototype.IPrototype;

public class Cubic implements IPrototype, Cloneable{

    private double length, width, height;

    public Cubic(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public Object cloneMe() throws CloneNotSupportedException {
        return super.clone();
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
