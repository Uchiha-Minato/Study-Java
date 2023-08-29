package com.lhy.dsp.flyweight;

public abstract class CircleShape {
    protected String color;
    protected int x;
    protected int y;
    protected int radius;

    public CircleShape(String color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public abstract void draw();
}
