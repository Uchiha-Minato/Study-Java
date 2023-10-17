package com.lhy.dsp.context;

import com.lhy.dsp.abstractexpression.Node;
import com.lhy.dsp.nonterminalexpression.DivNode;
import com.lhy.dsp.nonterminalexpression.ModNode;
import com.lhy.dsp.nonterminalexpression.MultipleNode;
import com.lhy.dsp.terminalexpression.ValueNode;

import java.util.Stack;

/**
 * 上下文
 * */
public class Calculator {
    private Node node;

    public void build(String statement) {
        Node left, right;
        Stack<Node> stack = new Stack<>();
        String[] statementArr = statement.split("");
        for (int i = 0; i < statementArr.length; i++) {
            if(statementArr[i].equalsIgnoreCase("*")) {
                left = stack.pop();
                int val = Integer.parseInt(statementArr[++i]);
                right = new ValueNode(val);
                stack.push(new MultipleNode(left, right));
            } else if (statementArr[i].equalsIgnoreCase("/")) {
                left = stack.pop();
                int val = Integer.parseInt(statementArr[++i]);
                right = new ValueNode(val);
                stack.push(new DivNode(left, right));
            } else if (statementArr[i].equalsIgnoreCase("%")) {
                left = stack.pop();
                int val = Integer.parseInt(statementArr[++i]);
                right = new ValueNode(val);
                stack.push(new ModNode(left, right));
            } else {
                stack.push(new ValueNode(Integer.parseInt(statementArr[i])));
            }
        }
        this.node = stack.pop();
    }

    public int compute() {
        return node.interpret();
    }
}
