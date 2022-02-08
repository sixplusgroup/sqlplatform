package org.example.node.table;

import org.example.CalculateScore;

import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public abstract class Table {

    public abstract float score();

    public abstract float score(Table t);

    /**
     * 在 list 里有 similar 的（用于计算分数和 edit 步骤）
     */
    public static Table isIn(Table e, List<Table> l) {
        Table res = null;
        String s = e.toString();
        int distance = s.length();
        for (Table item: l) {
            String tmp = item.toString();
            int d = CalculateScore.editDistance(s,tmp);
            if (d < distance && d < 0.5 * s.length()) {
                distance = d;
                res = item;
            }
        }
        return res;
    }

    public static boolean isStrictlyIn(Table e, List<Table> l){
        boolean flag = false;
        for (Table tmp: l){
            if (e.equals(tmp)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static boolean isStrictlyIn(List<JoinTable> l, JoinTable e) {
        boolean flag = false;
        for (JoinTable tmp: l){
            if (e.equals(tmp)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public abstract Table clone();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object t);

    @Override
    public abstract String toString();
}
