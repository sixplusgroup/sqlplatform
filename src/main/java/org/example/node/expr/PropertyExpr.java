package org.example.node.expr;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class PropertyExpr implements Expr {
    public String table;
    public String attribute;

    public PropertyExpr(){}

    public PropertyExpr(String table,String attribute){
        this.table = table;
        this.attribute = attribute;
    }
}
