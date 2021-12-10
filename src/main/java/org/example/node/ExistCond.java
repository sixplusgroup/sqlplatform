package org.example.node;

import com.alibaba.druid.sql.ast.expr.SQLExistsExpr;
import com.alibaba.druid.sql.repository.SchemaRepository;
import org.example.BuildAST;

/**
 * @author shenyichen
 * @date 2021/12/9
 **/
public class ExistCond extends Condition {
    public boolean not;
    public Select subQuery;

    public ExistCond(){

    }

    public ExistCond(SQLExistsExpr expr, SchemaRepository repository) {
        this.not = expr.not;
        subQuery = BuildAST.buildSelect(expr.subQuery.getQuery(),repository);
    }

    @Override
    protected ExistCond clone() throws CloneNotSupportedException {
        ExistCond cond = new ExistCond();
        cond.not = not;
        cond.subQuery = (Select) subQuery.clone();
        return cond;
    }
}
