package com.lhy.dsp.director;

import com.lhy.dsp.builder.IBuilder;

import javax.swing.*;

public class Director {

    private final IBuilder builder;

    public Director(IBuilder builder) {
        this.builder = builder;
    }

    public JPanel constructProduct() {
        builder.buildButton();
        builder.buildTextField();
        builder.buildLabel();
        return builder.getPanel();
    }
}
