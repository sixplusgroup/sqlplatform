package org.example.node;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLExistsExpr;
import com.alibaba.druid.sql.repository.SchemaRepository;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class Condition implements Node {
    public String value;

    public Condition(){

    }

    public Condition(String value) {
        this.value = value;
    }

    public static List<Condition> processCond(SQLExpr conds, SchemaRepository repository){
        // 忽略and, or的树结构，直接展平成一个list
        // TODO: condition处理方法；支持in, some, any
        List<Condition> res = new ArrayList<>();
        if (conds==null){
            return res;
        }
        if (conds instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr conditions = (SQLBinaryOpExpr) conds;
            SQLBinaryOperator operator = conditions.getOperator();
            SQLExpr left = conditions.getLeft();
            SQLExpr right = conditions.getRight();
            if (operator == SQLBinaryOperator.BooleanAnd || operator == SQLBinaryOperator.BooleanOr) {
                res.addAll(processCond(left,repository));
                res.addAll(processCond(right,repository));
            } else {// 表达式
                if (CommutativeCond.isCommutativeCond(operator.name)){
                    addCommutativeCond(res,operator.name,left.toString(),right.toString());
                } else {
                    UncommutativeCond cond = new UncommutativeCond(operator.name,left.toString(),right.toString());
                    res.add(cond);
                }
            }
        } else if (conds instanceof SQLExistsExpr) {
            res.add(new ExistCond((SQLExistsExpr) conds,repository));
        } else {
            res.add(new Condition(conds.toString()));
        }
        return res;
    }
    private static void addCommutativeCond(List<Condition> conditions, String operator, String left, String right) {
        for(Condition con: conditions) {
            if (con instanceof CommutativeCond) {
                CommutativeCond c = (CommutativeCond) con;
                if (c.operator.equals(operator)){
                    if (c.operands.contains(left)) {
                        c.operands.add(right);
                        return;
                    } else if (c.operands.contains(right)) {
                        c.operands.add(left);
                        return;
                    }
                }
            }
        }
        CommutativeCond c = new CommutativeCond(operator);
        c.operands.add(left);
        c.operands.add(right);
        conditions.add(c);
    }

    @Override
    protected Condition clone() throws CloneNotSupportedException {
        return new Condition(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CommutativeCond || obj instanceof UncommutativeCond || obj instanceof ExistCond){
            return false;
        }
        return value.equals(((Condition)obj).value);
    }
}
