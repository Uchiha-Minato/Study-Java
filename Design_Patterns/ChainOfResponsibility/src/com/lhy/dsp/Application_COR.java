package com.lhy.dsp;

import com.lhy.dsp.conchandler.Beijing;
import com.lhy.dsp.conchandler.Shanghai;
import com.lhy.dsp.conchandler.Tianjin;
import com.lhy.dsp.handler.IHandler;

public class Application_COR {
    public static void main(String[] args) {
        IHandler beijing, shanghai, tianjin;
        beijing = new Beijing();
        shanghai = new Shanghai();
        tianjin = new Tianjin();
        beijing.setNextHandler(shanghai);//建立责任链
        shanghai.setNextHandler(tianjin);

        beijing.handleRequest("京AKS987");
        beijing.handleRequest("沪H68979");
        beijing.handleRequest("津CXK987");
        beijing.handleRequest("辽B88881");
    }
}
