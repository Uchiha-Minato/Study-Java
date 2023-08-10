package com.lhy.dsp.concretebuilder;

import com.lhy.dsp.builder.IBuilder;
import com.lhy.dsp.product.PanelProduct;

import javax.swing.*;

public class ConcreteBuilder2 implements IBuilder {
    private final PanelProduct panel;

    public ConcreteBuilder2() {
        panel = new PanelProduct();
    }
    @Override
    public void buildButton() {
        panel.button = new JButton("Button");
    }

    @Override
    public void buildLabel() {
        panel.label = new JLabel("Label");
    }

    @Override
    public void buildTextField() {
        panel.textField = new JTextField("TextField");
    }

    @Override
    public JPanel getPanel() {
        panel.add(panel.textField);
        panel.add(panel.button);
        panel.add(panel.label);
        return panel;
    }
}
