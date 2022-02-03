package org.example.node.condition;


import org.example.CalculateScore;
import org.example.edit.CostConfig;
import org.example.node.expr.OtherExpr;

/**
 * @author shenyichen
 * @date 2022/1/19
 **/
public class OtherCond extends Condition {
    public String value;

    public OtherCond(){}

    public OtherCond(String value){
        super();
        this.value = value;
    }

    public OtherCond(boolean not, String value){
        this.not = not;
        this.value = value;
    }

    @Override
    public Condition rearrange() {
        return this;
    }

    @Override
    public float score() {
        int times = value.length() / CostConfig.other_digits;
        return CostConfig.other * times + (not ? CostConfig.not : 0);
    }

    @Override
    public float score(Condition c) {
        float score = 0;
        if (c instanceof OtherCond) {
            if (not) {
                if (c.not)
                    score += CostConfig.not;
            } else {
                if (c.not)
                    score -= CostConfig.not;
            }
            score += (score() - (not ? CostConfig.not : 0)) * (1 -
                    1.0f * CalculateScore.editDistance(c.toString(),this.toString())
                            / Math.max(c.toString().length(),this.toString().length()));
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
        return new OtherCond(not,value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OtherCond))
            return false;
        OtherCond o = (OtherCond) obj;
        return o.not == not && o.value.equals(value);
    }

    @Override
    public String toString() {
        String res = "";
        if (not)
            res += "not ";
        res += value;
        return res;
    }
}
