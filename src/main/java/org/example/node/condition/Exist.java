package org.example.node.condition;

import com.alibaba.druid.sql.ast.expr.SQLExistsExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.repository.SchemaRepository;
import org.example.BuildAST;
import org.example.Env;
import org.example.node.Select;

/**
 * @author shenyichen
 * @date 2021/12/9
 **/
public class Exist extends Condition {
    public Select subQuery;

    @Override
    public void flatten() {

    }

    public Exist(){

    }

    public Exist(boolean not, Select subQuery) {
        this.not = not;
        this.subQuery = subQuery;
    }

//    @Override
//    protected ExistCond clone() throws CloneNotSupportedException {
//        ExistCond cond = new ExistCond();
//        cond.not = not;
//        cond.subQuery = (Select) subQuery.clone();
//        return cond;
//    }
}
