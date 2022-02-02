package org.example.node.condition;


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
        return 0;
    }

    @Override
    public float score(Condition c) {
        return 0;
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
        return null;
    }
}
