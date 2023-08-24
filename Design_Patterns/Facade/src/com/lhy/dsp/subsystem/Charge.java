package com.lhy.dsp.subsystem;

public class Charge {
    private final int basicCharge = 12;
    CheckWord checkWord;

    public Charge(CheckWord checkWord) {
        this.checkWord = checkWord;
    }

    public void giveCharge() {
        int charge = checkWord.getAmount() * basicCharge;
        System.out.println("广告费用：" + charge + "元。");
    }
}
