package org.example.node.expr;

import com.alibaba.druid.sql.ast.SQLExpr;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合函数/其他函数的情况
 * @author shenyichen
 * @date 2022/1/17
 **/
public class FuncExpr implements Expr {
    public String name;
    public List<Expr> parameters;

    public FuncExpr(){
    }

    public FuncExpr(String name, List<SQLExpr> exprs){
        this.name = name;
        parameters = new ArrayList<>();
        for (SQLExpr expr: exprs) {
            parameters.add(Expr.build(expr));
        }
    }
}
