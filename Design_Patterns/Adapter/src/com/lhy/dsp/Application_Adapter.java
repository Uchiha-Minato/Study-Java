package com.lhy.dsp;

import com.lhy.dsp.adaptee.TwoElectricOutlet;
import com.lhy.dsp.adapter.ThreeElectricAdapter;
import com.lhy.dsp.target.ThreeElectricOutlet;

public class Application_Adapter {
    public static void main(String[] args) {
        ThreeElectricOutlet outlet3;//目标接口
        outlet3 = new Washing(); //可以直接使用接口
        System.out.println("使用三项插座接通电源");
        outlet3.connectElectricCurrent();

        TV tv = new TV();
        outlet3 = new ThreeElectricAdapter(tv);//先适配，再使用
        System.out.println("使用三项插座接通电源");
        outlet3.connectElectricCurrent();
    }

    static class TV implements TwoElectricOutlet {
        String name;

        TV() {
            name = "小米智能电视";
        }

        TV(String s) {
            name = s;
        }

        @Override
        public void connectElectricCurrent() {
            turnOn();
        }

        public void turnOn() {
            System.out.println(name + "开始播放节目。");
        }
    }

    static class Washing implements ThreeElectricOutlet {
        String name;

        Washing() {
            name = "工藤洗衣机";
        }

        Washing(String s) {
            name = s;
        }

        public void turnOn() {
            System.out.println(name + "开始洗衣服");
        }

        @Override
        public void connectElectricCurrent() {
            turnOn();
        }
    }
}
