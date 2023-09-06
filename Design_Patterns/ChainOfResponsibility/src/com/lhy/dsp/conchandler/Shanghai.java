package com.lhy.dsp.conchandler;

import com.lhy.dsp.handler.IHandler;

public class Shanghai implements IHandler {

    private IHandler nextHandler;

    @Override
    public void handleRequest(String request) {
        if (request.contains("沪"))
            System.out.println("车牌: " + request + "属于上海地区");
        else {
            System.out.println("车牌: " + request + "不属于上海地区");
            if (nextHandler != null)
                nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(IHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
