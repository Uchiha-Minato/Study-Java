package com.lhy.dsp;

import com.lhy.dsp.concreteprototype.Cubic;
import com.lhy.dsp.concreteprototype.Goat;

public class Application_Prototype {
    public static void main(String[] args) {
        Cubic cubic = new Cubic(12, 20, 66);
        System.out.print("cubic的长宽高：");
        System.out.printf("%.1f, %.1f, %.1f\n", cubic.getLength(), cubic.getWidth(), cubic.getHeight());

        try {
            Cubic cubicCopy = (Cubic) cubic.cloneMe();
            System.out.print("cubicCopy的长宽高：");
            System.out.printf("%.1f, %.1f, %.1f\n\n", cubicCopy.getLength(), cubicCopy.getWidth(), cubicCopy.getHeight());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        Goat goat = new Goat();
        goat.setColor(new StringBuffer("白色的山羊"));
        System.out.println("goat是" + goat.getColor());
        try {
            Goat goatCopy = (Goat) goat.cloneMe();
            System.out.println("goatCopy是" + goatCopy.getColor());
            System.out.println("goatCopy将自己的颜色改变");
            goatCopy.setColor(new StringBuffer("黑色的山羊"));
            System.out.println("goat仍然是" + goat.getColor());
            System.out.println("goatCopy是" + goatCopy.getColor());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
