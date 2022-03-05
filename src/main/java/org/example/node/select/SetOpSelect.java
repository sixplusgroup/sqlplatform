package org.example.node.select;

import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.statement.SQLUnionOperator;
import org.example.Env;
import org.example.edit.CostConfig;
import org.example.enums.SetOp;
import org.example.node.orderby.OrderBy;
import org.example.node.table.Table;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public class SetOpSelect extends Select {
    public Select left;
    public Select right;
    public SetOp operator;
    public OrderBy orderBy;

    public SetOpSelect(){}

    @Override
    public void setOuterSelect(PlainSelect outerSelect) {
        left.setOuterSelect(outerSelect);
        right.setOuterSelect(outerSelect);
    }

    // todo 类比CompoundCond，交换？多个？ sort?

    public SetOpSelect(Select left, Select right, SQLUnionOperator operator, SQLOrderBy orderBy, Env env) {
        this.left = left;
        this.right = right;
        this.operator = SetOp.valueOf(operator.toString().replaceAll(" ","_"));
        this.orderBy = orderBy==null ? null : new OrderBy(orderBy);
        // 统计量赋值
        subqueries = new ArrayList<>();
        subqueries.addAll(left.subqueries);
        subqueries.addAll(right.subqueries);
        tableAliasMap = new HashMap<>();
        tableAliasMap.putAll(left.tableAliasMap);
        tableAliasMap.putAll(right.tableAliasMap);
        attrAliasMap = new HashMap<>();
        attrAliasMap.putAll(left.attrAliasMap);
        attrAliasMap.putAll(right.attrAliasMap);
    }

    @Override
    public float score() {
        return CostConfig.set_operator
                + (orderBy==null ? 0 : orderBy.score())
                + left.score()
                + right.score();
    }

    @Override
    public float score(Table t) {
        while (tableAliasMap.containsKey(t.toString())) {
            t = tableAliasMap.get(t.toString());
        }
        if (!(t instanceof Select)) {
           return 0;
        }
        Select s = (Select) t;
        float score = Math.max(left.score(s), right.score(s));
        if (s instanceof SetOpSelect) {
            SetOpSelect ss = (SetOpSelect) s;
            score = Math.max(score,
                    left.score(ss.left)
                            + right.score(ss.right)
                            + (operator.equals(ss.operator) ? CostConfig.set_operator : 0)
                            + (orderBy==null
                                ? (ss.orderBy==null ? 0 : -ss.orderBy.score())
                                : orderBy.score(ss.orderBy)));
            score = Math.max(score,
                    score(ss.left) - (ss.score() - ss.left.score()));
            score = Math.max(score,
                    score(ss.right) - (ss.score() - ss.right.score()));
        }
        return score;
    }

    @Override
    public SetOpSelect clone() {
        SetOpSelect select = new SetOpSelect();
        select.left = left.clone();
        select.right = right.clone();
        select.operator = operator;
        if (orderBy != null)
            select.orderBy = orderBy.clone();
        select.subqueries = subqueries;
        select.tableAliasMap = tableAliasMap;
        select.attrAliasMap = attrAliasMap;
        return select;
    }

    @Override
    public int hashCode() {
        return operator.hashCode();
    }

    @Override
    public boolean equals(Object t) {
        if (!(t instanceof SetOpSelect))
            return false;
        SetOpSelect ss = (SetOpSelect) t;
        return ss.operator.equals(operator) && ss.left.equals(left) && ss.right.equals(right)
                && ((ss.orderBy == null && orderBy == null) || (ss.orderBy != null && ss.orderBy.equals(orderBy)));
    }

    @Override
    public String toString() {
        if (operator != SetOp.EXCEPT && left.toString().compareTo(right.toString()) > 0)
            return right.toString() + " " + operator.toString() + " " + left.toString();
        return left.toString() + " " + operator.toString() + " " + right.toString();
    }

}
