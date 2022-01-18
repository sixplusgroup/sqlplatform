package org.example.node.condition;

import com.alibaba.druid.sql.ast.SQLExpr;
import org.example.Env;
import org.example.node.condition.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class CompoundCond extends Condition {

    public List<Condition> subConds;

    public CompoundCond() {
    }

    public CompoundCond(String operator, Condition left, Condition right) {
        this.operator = operator;
        this.subConds = new ArrayList<>();
        this.subConds.add(left);
        this.subConds.add(right);
    }

    @Override
    public void flatten() {

    }

}
