package com.lhy.dsp;

import com.lhy.dsp.facade.ClientServerFacade;

public class Application_Facade {
    public static void main(String[] args) {
        ClientServerFacade facade;
        String clientAdvertisement = "原神是米哈游自主研发的...";
        facade = new ClientServerFacade(clientAdvertisement);
        facade.doAdvertisement();
    }
}
