package org.example;

import com.alibaba.druid.sql.repository.SchemaRepository;
import org.example.node.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class Canonicalizer {

    public static void canonicalize(Node ast) {
        if (ast instanceof SetOpSelect) {
            canonicalize(((SetOpSelect) ast).left);
            canonicalize(((SetOpSelect) ast).right);
        } else if (ast instanceof PlainSelect) {
            PlainSelect select = (PlainSelect) ast;
            Collections.sort(select.selections);
        }
    }
}
