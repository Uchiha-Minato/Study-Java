package com.lhy.dsp.terminalexpression;

import com.lhy.dsp.abstractexpression.Node;

/**
 * 数值结点，终结符
 * */
public class ValueNode implements Node {
    private final int value;

    public ValueNode(int value) {
        this.value = value;
    }
    @Override
    public int interpret() {
        return this.value;
    }
}
