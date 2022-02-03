package org.example.node.condition;

import org.example.CalculateScore;
import org.example.edit.CostConfig;
import org.example.node.select.Select;

/**
 * @author shenyichen
 * @date 2021/12/9
 **/
public class Exist extends Condition {
    public Select subQuery;

    public Exist(){

    }

    public Exist(boolean not, Select subQuery) {
        this.not = not;
        this.subQuery = subQuery;
        Select.subQueryProcess(this.subQuery);
    }

    @Override
    public Condition rearrange() {
        return this;
    }

    @Override
    public float score() {
        return subQuery.score() + (not ? CostConfig.not : 0);
    }

    @Override
    public float score(Condition c) {
        float score = 0;
        if (c instanceof Exist) {
            if (not) {
                if (c.not)
                    score += CostConfig.not;
            } else {
                if (c.not)
                    score -= CostConfig.not;
            }
            score += subQuery.score(((Exist) c).subQuery);
        }
        else if (c instanceof CompoundCond) {
            CompoundCond cc = (CompoundCond) c;
            Condition match = Condition.isIn(this,cc.subConds);
            if (match != null) {
                score = score(match) - (cc.score() - match.score()) * CostConfig.delete_cost_rate;
            }
        }
        return score;
    }

    @Override
    public Condition clone() {
        return new Exist(not,subQuery.clone());
    }

    @Override
    public int hashCode() {
        return subQuery.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Exist))
            return false;
        Exist e = (Exist) obj;
        return e.not==not && e.subQuery.equals(subQuery);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (not)
            sb.append("not exist ");
        sb.append(subQuery.toString());
        return sb.toString();
    }

}
