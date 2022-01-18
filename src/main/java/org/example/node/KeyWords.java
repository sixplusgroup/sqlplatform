package org.example.node;

import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;

import java.util.HashMap;

/**
 * @author shenyichen
 * @date 2022/1/14
 **/
public class KeyWords {
    public HashMap<String,Integer> keywords;

    public KeyWords() {
        this.keywords = new HashMap<>();
    }

    public KeyWords(SQLSelectQueryBlock query){

    }
}
