package com.lhy.dsp.factory;

import com.lhy.dsp.concreteflyweight.Circle;
import com.lhy.dsp.flyweight.CircleShape;

import java.util.HashMap;

public class CircleFactory {
    private static final HashMap<String, CircleShape> circleMap = new HashMap<>();

    public static CircleShape getCircle(String color) {
        Circle circle = (Circle) circleMap.get(color);

        if(circle==null) {
            circle = new Circle(color);
            circleMap.put(color, circle);
            System.out.printf("Created %s circle.", color);
        }
        return circle;
    }
}
