package org.example.node.condition;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public abstract class AtomCond extends Condition {
    public String operator;
    public String opOriginStr;

    public AtomCond(String originStr) {
        super(originStr);
    }
}
