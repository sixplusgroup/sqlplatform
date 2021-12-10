package org.example.node;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class Table implements Node {
    public String name;

    public Table(){}

    public Table(String name) {
        this.name = name;
    }

    @Override
    protected Table clone() throws CloneNotSupportedException {
        return new Table(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object t) {
        if (t instanceof Table){
            return name.equals(((Table) t).name);
        }
        return false;
    }
}
