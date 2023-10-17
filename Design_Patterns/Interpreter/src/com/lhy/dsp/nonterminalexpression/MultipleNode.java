package com.lhy.dsp.nonterminalexpression;

import com.lhy.dsp.abstractexpression.Node;

public class MultipleNode extends SymbolNode{
    public MultipleNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public int interpret() {
        return super.left.interpret() * super.right.interpret();
    }
}
