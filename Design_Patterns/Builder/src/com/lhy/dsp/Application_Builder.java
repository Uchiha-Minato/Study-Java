package com.lhy.dsp;

import com.lhy.dsp.builder.IBuilder;
import com.lhy.dsp.concretebuilder.ConcreteBuilder1;
import com.lhy.dsp.concretebuilder.ConcreteBuilder2;
import com.lhy.dsp.director.Director;

import javax.swing.*;

public class Application_Builder {
    public static void main(String[] args) {
        IBuilder builder = new ConcreteBuilder1();
        Director director = new Director(builder);
        JPanel panel = director.constructProduct();
        JFrame frame1 = new JFrame();
        frame1.add(panel);
        frame1.setBounds(12,12,200,120);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setVisible(true);

        builder = new ConcreteBuilder2();
        director = new Director(builder);
        panel = director.constructProduct();
        JFrame frame2 = new JFrame();
        frame2.add(panel);
        frame2.setBounds(212,12,200,120);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setVisible(true);
    }
}
