package com.lhy.dsp.facade;

import com.lhy.dsp.subsystem.Charge;
import com.lhy.dsp.subsystem.CheckWord;
import com.lhy.dsp.subsystem.TypeSetting;

public class ClientServerFacade {
    private CheckWord checkWord;
    private Charge charge;
    private TypeSetting typeSetting;
    private final String advertisement;


    public ClientServerFacade(String advertisement) {
        this.advertisement = advertisement;
        checkWord = new CheckWord(this.advertisement);
        charge = new Charge(checkWord);
        typeSetting = new TypeSetting(this.advertisement);
    }

    public void doAdvertisement() {
        checkWord.setChargeAmount();
        charge.giveCharge();
        typeSetting.typeSetting();
    }
}
