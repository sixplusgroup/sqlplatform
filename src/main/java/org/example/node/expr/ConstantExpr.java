package org.example.node.expr;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class ConstantExpr implements Expr {
    public String value;

    public ConstantExpr(){}

    public ConstantExpr(String value) {
        this.value = value;
    }
}
