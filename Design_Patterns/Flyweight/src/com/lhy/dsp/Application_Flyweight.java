package com.lhy.dsp;

import com.lhy.dsp.concreteflyweight.Circle;
import com.lhy.dsp.factory.CircleFactory;

public class Application_Flyweight {

    private static final String[] colors = {"Red", "Green", "Blue", "White", "Black"};

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Circle circle = (Circle) CircleFactory.getCircle(getRandomColor());
            circle.setX(getRandomX());
            circle.setY(getRandomY());
            circle.setRadius(100);
            circle.draw();
        }
        System.out.println("取出一个蓝色的圆：");
        Circle circle1 = (Circle) CircleFactory.getCircle("Blue");
        circle1.draw();
    }

    private static String getRandomColor() {
        return colors[(int) (Math.random() * colors.length)];
    }

    private static int getRandomX() {
        return (int) (Math.random() * 100);
    }

    private static int getRandomY() {
        return (int) (Math.random() * 100);
    }
}
