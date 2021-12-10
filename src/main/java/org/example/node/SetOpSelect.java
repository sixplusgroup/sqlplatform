package org.example.node;

import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLUnionOperator;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import com.alibaba.druid.sql.repository.SchemaRepository;
import org.example.node.enums.SetOp;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public class SetOpSelect extends Select {
    public String name;
    public Select left;
    public Select right;
    public SetOp operator;
    public OrderBy orderBy;

    public SetOpSelect(){}

    public SetOpSelect(Select left, Select right, SQLUnionOperator operator, SQLOrderBy orderBy, SchemaRepository repository) {
        this.name = operator.name + "-" + left.name + "-" + right.name;
        this.left = left;
        this.right = right;
        this.operator = SetOp.valueOf(operator.toString());
        this.orderBy = orderBy==null ? null : new OrderBy(orderBy);
    }

    @Override
    protected SetOpSelect clone() throws CloneNotSupportedException {
        SetOpSelect select = new SetOpSelect();
        select.name = this.name;
        select.left = (Select) this.left.clone();
        select.right = (Select) this.right.clone();
        select.operator = operator;
        select.orderBy = (OrderBy) this.orderBy.clone();
        return select;
    }

//    @Override
//    public int hashCode() {
//        int hashCode = 1;
//        hashCode = 31*hashCode + (left==null?0:left.hashCode());
//        hashCode = 31*hashCode + (right==null?0:right.hashCode());
//        hashCode = 31*hashCode + (operator==null?0:operator.hashCode());
//        return super.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object t) {
//        if (t instanceof SetOpSelect){
//            SetOpSelect query = (SetOpSelect) t;
//            if (!(operator.equals(query.operator)))
//                return false;
//            if ()
//        }
//        return false;
//    }
}
