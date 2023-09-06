package com.lhy.dsp.conchandler;

import com.lhy.dsp.handler.IHandler;

public class Tianjin implements IHandler {

    private IHandler nextHandler;
    @Override
    public void handleRequest(String request) {
        if (request.contains("津"))
            System.out.println("车牌: " + request + "属于天津地区");
        else {
            System.out.println("车牌: " + request + "不属于天津地区");
            if (nextHandler != null)
                nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(IHandler nextIHandler) {
        this.nextHandler = nextIHandler;
    }
}
