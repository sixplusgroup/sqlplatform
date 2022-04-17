package org.example.node.select;

import org.example.node.expr.Expr;
import org.example.node.table.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public abstract class Select extends Table {
    public HashMap<String, Table> tableAliasMap; // from里来
    public HashMap<String, Expr> attrAliasMap; // selections里来

    public Select(String originStr) {
        super(originStr);
        tableAliasMap = new HashMap<>();
        attrAliasMap = new HashMap<>();
    }

    /**
     * exist子句中selections没有比较意义，设为空
     * @param s
     * @return
     */
    public static void subQueryProcess(Select s) {
        if (s instanceof SetOpSelect){
            subQueryProcess(((SetOpSelect) s).left);
            subQueryProcess(((SetOpSelect) s).right);
        }
        else { // PlainSelect
            PlainSelect ps = (PlainSelect) s;
            ps.distinct = false;
            ps.selections = new ArrayList<>();
        }
    }

    public abstract void setOuterSelect(PlainSelect outerSelect);

    @Override
    public abstract Select clone();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object t);
}
