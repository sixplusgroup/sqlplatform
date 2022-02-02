package org.example;

import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.select.SetOpSelect;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class Canonicalizer {

    public static void canonicalize(Select ast) {
        if (ast instanceof SetOpSelect) {
            canonicalize(((SetOpSelect) ast).left);
            canonicalize(((SetOpSelect) ast).right);
        } else if (ast instanceof PlainSelect) {
            PlainSelect select = (PlainSelect) ast;
//            Collections.sort(select.selections);
        }
    }


}
