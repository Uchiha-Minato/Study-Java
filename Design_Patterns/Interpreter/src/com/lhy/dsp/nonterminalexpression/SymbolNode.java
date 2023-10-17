package com.lhy.dsp.nonterminalexpression;

import com.lhy.dsp.abstractexpression.Node;

/**
 * 表达式结点 非终结符
 * */
public abstract class SymbolNode implements Node {
    protected Node left;
    protected Node right;

    public SymbolNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
}
