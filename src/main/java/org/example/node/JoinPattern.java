package org.example.node;

import org.example.node.enums.JoinType;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class JoinPattern implements Node {
    String left;
    String right;
    JoinType joinType;

    public JoinPattern(String left, String right, JoinType joinType) {
        this.left = left;
        this.right = right;
        this.joinType = joinType;
    }

    @Override
    protected JoinPattern clone() throws CloneNotSupportedException {
        return new JoinPattern(left,right,joinType);
    }
}
