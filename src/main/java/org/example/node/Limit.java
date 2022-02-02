package org.example.node;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import org.example.BuildAST;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class Limit {
    private static Logger logger = Logger.getLogger(BuildAST.class.getName());

    public Integer rowCount;
    public Integer offset;

    public Limit(Integer rowCount, Integer offset) {
        this.rowCount = rowCount;
        this.offset = offset;
    }

    public Limit(SQLLimit limit){
        if (limit==null){
            rowCount = null;
            offset = null;
            return;
        }
        SQLExpr rowCount = limit.getRowCount();
        SQLExpr offset = limit.getOffset();
        if (rowCount==null){
            this.rowCount = null;
        } else if (rowCount instanceof SQLIntegerExpr) {
            this.rowCount = ((SQLIntegerExpr) rowCount).getNumber().intValue();
        } else {
            logger.log(Level.WARNING,"Limit rowCount is not int value:" + rowCount.toString());
            this.rowCount = null;
        }
        if (offset==null){
            this.offset = null;
        } else if (offset instanceof SQLIntegerExpr) {
            this.offset = ((SQLIntegerExpr) offset).getNumber().intValue();
        } else {
            logger.log(Level.WARNING,"Limit offset is not int value:" + offset.toString());
            this.offset = null;
        }
    }

    @Override
    public Limit clone() {
        return new Limit(rowCount,offset);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if (rowCount!=null)
            hash += rowCount.hashCode()*31;
        if (offset!=null)
            hash += offset.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Limit))
            return false;
        Limit limit = (Limit) obj;
        if (limit.offset==null){
            if (offset!=null)
                return false;
            if (limit.rowCount==null)
                return rowCount==null;
            else
                return limit.rowCount.equals(rowCount);
        }
        return limit.rowCount.equals(rowCount) && limit.offset.equals(offset);
    }
}
