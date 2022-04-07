package org.example.edit;

import javafx.util.Pair;
import org.example.Env;
import org.example.node.select.PlainSelect;

import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public interface Edit {
    List<Pair<PlainSelect,Float>> add(PlainSelect instr, PlainSelect stu, Env env);
    List<Pair<PlainSelect,Float>> remove(PlainSelect instr, PlainSelect stu, Env env);
    List<Pair<PlainSelect,Float>> edit(PlainSelect instr, PlainSelect stu, Env env) throws Exception;
    List<String> hintAdd(PlainSelect now, PlainSelect prev, Env env);
    List<String> hintRemove(PlainSelect now, PlainSelect prev, Env env);
    List<String> hintEdit(PlainSelect now, PlainSelect prev, Env env) throws Exception;
}
