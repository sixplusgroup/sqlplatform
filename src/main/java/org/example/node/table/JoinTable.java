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
    // todo sort

    public JoinTable(Table left, Table right, JoinType joinType) {
        this.left = left;
        this.right = right;
        this.joinType = joinType;
    }

    @Override
    public float score() {
        return CostConfig.join_pattern;
    }

    @Override
    public float score(Table t) {
        // case 1: this includes t
        float score = Math.max(left.score(t), right.score(t));
        if (t instanceof JoinTable) {
            JoinTable jt = (JoinTable) t;
            // case 2: this equals t
            score = Math.max(score,
                    left.score(jt.left) * (1/3 * score())
                            + right.score(jt.right) * (1/3 * score())
                            + (joinType.equals(jt.joinType) ? score()*1/3 : 0));
            // case 3: e includes this
            score = Math.max(score,
                    score(jt.left) - CostConfig.join_pattern*1/3*(jt.score()-jt.left.score()));
            score = Math.max(score,
                    score(jt.right) - CostConfig.join_pattern*1/3*(1+jt.left.score()));
        }
        return score;
    }

    @Override
    public JoinTable clone() {
        return new JoinTable(left.clone(),right.clone(),joinType);
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
        return jt.left.equals(left) && jt.right.equals(right) && jt.joinType.equals(joinType);
    }

    @Override
    public String toString() {
        return left + " " + joinType.toString() + " " + right;
    }
}
