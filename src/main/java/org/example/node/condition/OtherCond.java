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
    @Override
    public Condition rearrange() {
        return this;
    }
}
