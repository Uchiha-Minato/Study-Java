package com.lhy.dsp;

import com.lhy.dsp.abstractFactory.CoreCreator;
import com.lhy.dsp.abstractProduct.PenCore;
import com.lhy.dsp.concreteFactory.BlackCoreCreator;
import com.lhy.dsp.concreteFactory.BlueCoreCreator;
import com.lhy.dsp.concreteFactory.RedCoreCreator;

public class Application {
    public static void main(String[] args) {
        PenCore penCore; //笔芯
        CoreCreator creator; //笔芯工厂
        BallPen ballPen = new BallPen();//圆珠笔实例

        creator = new RedCoreCreator(); //向下转型
        penCore = creator.getPenCore(); //使用工厂方法返回笔芯
        ballPen.usePenCore(penCore);
        ballPen.write("niuBi");

        creator = new BlueCoreCreator();
        penCore = creator.getPenCore();
        ballPen.usePenCore(penCore);
        ballPen.write("你好");

        creator = new BlackCoreCreator();
        penCore = creator.getPenCore();
        ballPen.usePenCore(penCore);
        ballPen.write("不跟你多bb");
    }
}
