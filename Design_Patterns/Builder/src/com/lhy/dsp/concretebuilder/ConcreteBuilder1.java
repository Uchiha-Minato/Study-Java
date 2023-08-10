package com.lhy.dsp.concretebuilder;

import com.lhy.dsp.builder.IBuilder;
import com.lhy.dsp.product.PanelProduct;

import javax.swing.*;

public class ConcreteBuilder1 implements IBuilder {

    private final PanelProduct panel;

    public ConcreteBuilder1() {
        panel = new PanelProduct();
    }
    @Override
    public void buildButton() {
        panel.button = new JButton("按钮");
    }

    @Override
    public void buildLabel() {
        panel.label = new JLabel("标签");
    }

    @Override
    public void buildTextField() {
        panel.textField = new JTextField("文本框");
    }

    @Override
    public JPanel getPanel() {
        panel.add(panel.button);
        panel.add(panel.label);
        panel.add(panel.textField);
        return panel;
    }
}
