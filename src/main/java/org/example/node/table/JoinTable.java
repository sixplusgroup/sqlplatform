package org.example.node.table;

import org.example.edit.CostConfig;
import org.example.enums.JoinType;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class JoinTable extends Table {
    Table left;
    Table right;
    JoinType joinType;

    public JoinTable(Table left, Table right, JoinType joinType, String originStr) {
        super(originStr);
        this.left = left;
        this.right = right;
        this.joinType = joinType;
    }

    @Override
    public float score() {
        return CostConfig.join_type + left.score() + right.score();
    }

    @Override
    public float score(Table t) {
        // case 1: this includes t
        float score = Math.max(left.score(t), right.score(t));
        if (t instanceof JoinTable) {
            JoinTable jt = (JoinTable) t;
            // case 2: this equals t
            score = Math.max(score,
                    left.score(jt.left) + right.score(jt.right)
                            + (joinType.equals(jt.joinType) ? CostConfig.join_type : 0));
            score = Math.max(score,
                    left.score(jt.right) + right.score(jt.left)
                            + (joinType.equals(jt.joinType) ? CostConfig.join_type : 0));
            // case 3: e includes this
            score = Math.max(score,
                    score(jt.left) - (jt.score() - jt.left.score()) * CostConfig.delete_cost_rate);
            score = Math.max(score,
                    score(jt.right) - (jt.score() - jt.right.score()) * CostConfig.delete_cost_rate);
        }
        return score;
    }

    @Override
    public JoinTable clone() {
        return new JoinTable(left.clone(), right.clone(), joinType, originStr);
    }

    @Override
    public int hashCode() {
        return joinType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JoinTable))
            return false;
        JoinTable jt = (JoinTable) obj;
        return (jt.left.equals(left) && jt.right.equals(right) && jt.joinType.equals(joinType))
                || (jt.left.equals(right) && jt.right.equals(left) && jt.joinType.equals(joinType));
    }

    @Override
    public String toString() {
        String left_s = left.toString();
        String right_s = right.toString();
        int res = left_s.compareTo(right_s);
        if (res >= 0)
            return left_s + " " + joinType.toString() + " " + right_s;
        else
            return right_s + " " + joinType.toString() + " " + left_s;
    }
}
