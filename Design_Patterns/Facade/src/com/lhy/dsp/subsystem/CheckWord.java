package com.lhy.dsp.subsystem;

public class CheckWord {
    private final int basicAmount = 85;
    private final String advertisement;
    private int amount;

    public CheckWord(String advertisement) {
        this.advertisement = advertisement;
    }

    public void setChargeAmount() {
        amount = advertisement.length() + basicAmount;
    }

    public int getAmount() {
        return amount;
    }
}
