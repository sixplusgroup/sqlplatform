package org.example;

import org.example.node.*;
import org.example.node.expr.Expr;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class Canonicalizer {

    public static void canonicalize(Expr ast) {
        if (ast instanceof SetOpSelect) {
            canonicalize(((SetOpSelect) ast).left);
            canonicalize(((SetOpSelect) ast).right);
        } else if (ast instanceof PlainSelect) {
            PlainSelect select = (PlainSelect) ast;
//            Collections.sort(select.selections);
        }
    }


}
