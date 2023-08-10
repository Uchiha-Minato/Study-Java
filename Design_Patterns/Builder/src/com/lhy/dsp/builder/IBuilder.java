package com.lhy.dsp.builder;

import javax.swing.*;

public interface IBuilder {
    void buildButton();
    void buildLabel();
    void buildTextField();
    JPanel getPanel();
}
