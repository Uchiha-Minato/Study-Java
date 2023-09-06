package com.lhy.dsp.conchandler;

import com.lhy.dsp.handler.IHandler;

public class Beijing implements IHandler {

    private IHandler nextHandler; //存放后继者的引用

    @Override
    public void handleRequest(String request) {
        if (request.contains("京"))
            System.out.println("车牌: " + request + "属于北京地区");
        else {
            System.out.println("车牌: " + request + "不属于北京地区");
            if (nextHandler != null)
                nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(IHandler nextIHandler) {
        this.nextHandler = nextIHandler;
    }
}
