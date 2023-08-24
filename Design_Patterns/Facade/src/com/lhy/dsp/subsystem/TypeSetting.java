package com.lhy.dsp.subsystem;

public class TypeSetting {
    String advertisement;

    public TypeSetting(String advertisement) {
        this.advertisement = advertisement;
    }

    public void typeSetting() {
        System.out.println("广告排版格式：");
        System.out.println("***********");
        System.out.println(advertisement);
        System.out.println("***********");
    }
}
