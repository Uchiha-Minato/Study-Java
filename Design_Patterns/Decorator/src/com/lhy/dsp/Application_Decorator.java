package com.lhy.dsp;

import com.lhy.dsp.component.Bird;
import com.lhy.dsp.concretecomponent.Sparrow;
import com.lhy.dsp.concretedecorator.ElectricWings;

public class Application_Decorator {
    public static void main(String[] args) {
        Bird bird = new Sparrow();
        System.out.println("没有安装电子翅膀的麻雀飞行距离：" + bird.fly());

        Bird bird1 = new ElectricWings(bird); //给原本的小鸟装上一个翅膀
        System.out.println("安装了一个电子翅膀的麻雀飞行距离：" + bird1.fly());

        Bird bird2 = new ElectricWings(bird1); //给已经装上一个翅膀的小鸟多装一个
        System.out.println("安装了2个电子翅膀的麻雀飞行距离：" + bird2.fly());
    }
}
