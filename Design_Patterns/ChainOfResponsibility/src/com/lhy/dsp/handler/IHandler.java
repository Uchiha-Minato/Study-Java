package com.lhy.dsp.handler;

public interface IHandler {
    void handleRequest(String request);
    void setNextHandler(IHandler nextHandler);
}
