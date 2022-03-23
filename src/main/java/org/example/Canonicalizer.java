package org.example;

import javafx.util.Pair;
import org.example.node.condition.*;
import org.example.node.expr.*;
import org.example.node.orderby.OrderByItem;
import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.select.SetOpSelect;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class Canonicalizer {
    public static void canonicalize(Select ast, Env env) {
        if (ast instanceof SetOpSelect) {
            canonicalize(((SetOpSelect) ast).left, env);
            canonicalize(((SetOpSelect) ast).right, env);
        } else if (ast instanceof PlainSelect) {
            PlainSelect select = (PlainSelect) ast;
            canonicalizeOrderBy(select, env);
        }
    }

    public static void canonicalizeOrderBy(PlainSelect ps, Env env) {
        for (Map.Entry<String, List<Pair<String, OrderByItem>>> item: separateOrderByItemByTable(ps).entrySet()) {
            String[] table = item.getKey().split(":");
            if (env.primaryKeys.containsKey(table[0])) {
                List<String> pkeys = new ArrayList<>(env.primaryKeys.get(table[0]));
                for (Pair<String, OrderByItem> pair: item.getValue()) {
                    if (pkeys.contains(pair.getKey())) {
                        pkeys.remove(pair.getKey());
                        continue;
                    }
                    // 如果包含了这个特定表的全部主键后，还有多余，则多余的全都应当规则化掉
                    if (pkeys.size() == 0) {
                        ps.orderBy.items.remove(pair.getValue());
                    }
                }
            }
        }
    }

    /**
     * HashMap<表名:表名alias, List<Pair<属性名, 对应OrderByItem>>>
     * @param ps
     * @return
     */
    private static HashMap<String, List<Pair<String, OrderByItem>>> separateOrderByItemByTable(PlainSelect ps) {
        HashMap<String, List<Pair<String, OrderByItem>>> map = new HashMap<>();
        for (OrderByItem item: ps.orderBy.items) {
            Expr expr = item.column;
            if (expr instanceof AtomExpr) {
                String alias = ((AtomExpr) expr).value;
                if (ps.attrAliasMap.containsKey(alias)) {
                    expr = ps.attrAliasMap.get(alias);
                }
            }
            if (expr instanceof PropertyExpr) {
                String tableAlias = ((PropertyExpr) expr).table.toString();
                String tableName = tableAlias;
                if (ps.tableAliasMap.containsKey(tableAlias)) {
                    tableName = ps.tableAliasMap.get(tableAlias).toString();
                }
                String key = tableName + ":" + tableAlias;
                if (! map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(new Pair<>(((PropertyExpr) expr).attribute.toString(), item));
            }
        }
        return map;
    }

    public static void substituteEqualClass(Select ast) {
        if (ast instanceof SetOpSelect) {
            substituteEqualClass(((SetOpSelect) ast).left);
            substituteEqualClass(((SetOpSelect) ast).right);
        } else {
            PlainSelect ps = (PlainSelect) ast;
            List<List<Expr>> equalClasses = getEqualClasses(ps.where);
            substituteEqualClass(ps, equalClasses);
        }
    }

    public static void substituteEqualClass(Select ast, List<List<Expr>> equalClasses) {
        if (ast instanceof SetOpSelect) {
            substituteEqualClass(((SetOpSelect) ast).left, equalClasses);
            substituteEqualClass(((SetOpSelect) ast).right, equalClasses);
            return;
        }
        PlainSelect ps = (PlainSelect) ast;
        substituteExprList(equalClasses, ps.selections);
        if (ps.where != null)
            substituteCondition(equalClasses, ps.where);
        substituteExprList(equalClasses, ps.groupBy.items);
        if (ps.groupBy.having != null)
            substituteCondition(equalClasses, ps.groupBy.having);
        for (OrderByItem item: ps.orderBy.items) {
            item.column = substituteExprs(equalClasses, item.column);
        }
    }

    public static List<List<Expr>> getEqualClasses(Condition where) {
        List<List<Expr>> equalClasses = new ArrayList<>();
        if (where instanceof CompoundCond) {
            if (where.operator.equals("AND")) {
                List<Condition> subConds = ((CompoundCond) where).getSubConds();
                for (Condition c: subConds) {
                    equalClasses.addAll(getEqualClasses(c));
                }
            }
        } else if (where instanceof CommutativeCond && where.operator.equals("=")) {
            equalClasses.add(((CommutativeCond) where).operands);
        }
        for (List<Expr> equalClass: equalClasses) {
            equalClass.sort(Comparator.comparing(Expr::toString));
        }
        return equalClasses;
    }

    public static void substituteCondition(List<List<Expr>> equalClasses, Condition c) {
        if (c instanceof CompoundCond) {
            List<List<Expr>> newEqualClasses = equalClasses;
            // additional equal classes
            if (c.operator.equals("AND") && c.father != null && c.father.operator.equals("OR")) {
                List<List<Expr>> additionalEquals = new ArrayList<>();
                for (Condition subCond: ((CompoundCond) c).getSubConds()) {
                    if (subCond instanceof CommutativeCond && subCond.operator.equals("=")) {
                        additionalEquals.add(((CommutativeCond) subCond).operands);
                    }
                }
                if (additionalEquals.size() > 0) {
                    newEqualClasses = mergeEqualClasses(equalClasses, additionalEquals);
                }
            }
            // substitute
            for (Condition subCond: ((CompoundCond) c).getSubConds()) {
                substituteCondition(newEqualClasses, subCond);
            }
        } else if (c instanceof CommutativeCond) {
            if (!c.operator.equals("=")) {
                substituteExprList(equalClasses, ((CommutativeCond) c).operands);
            }
        } else if (c instanceof UncommutativeCond) {
            substituteExprs(equalClasses, ((UncommutativeCond) c).left);
            substituteExprs(equalClasses, ((UncommutativeCond) c).right);
        } else if (c instanceof Exist) {
            substituteEqualClass(((Exist) c).subQuery, equalClasses);
        }
    }

    public static void substituteExprList(List<List<Expr>> equalClasses, List<Expr> l) {
        for (int i=0;i<l.size();i++) {
            l.set(i, substituteExprs(equalClasses, l.get(i)));
        }
    }

    public static Expr substituteExprs(List<List<Expr>> equalClasses, Expr e) {
        Expr res = e;
        for (List<Expr> equalClass: equalClasses) {
            res = substituteExpr(equalClass, res);
        }
        return res;
    }

    public static Expr substituteExpr(List<Expr> equalClass, Expr e) {
        if (Expr.isDirectlyStrictlyIn(e, equalClass))
            return equalClass.get(0);
        if (e instanceof FuncExpr || e instanceof ListExpr) {
            List<Expr> params;
            if (e instanceof FuncExpr) {
                params = ((FuncExpr) e).parameters;
            } else {
                params = ((ListExpr) e).exprs;
            }
            for (int i=0;i<params.size();i++) {
                params.set(i, substituteExpr(equalClass, params.get(i)));
            }
        }
        return e;
    }

    private static List<List<Expr>> mergeEqualClasses(List<List<Expr>> old, List<List<Expr>> addition) {
        List<List<Expr>> res = new ArrayList<>();
        for (List<Expr> item: old) {
            res.add(new ArrayList<>(item));
        }
        for (List<Expr> item: addition) {
            boolean overlap = false;
            for (List<Expr> resItem: res) {
                if (overlap(item, resItem)) {
                    overlap = true;
                    resItem.removeAll(item);
                    resItem.addAll(item);
                }
            }
            if (!overlap) {
                res.add(item);
            }
        }
        for (List<Expr> equalClass: res) {
            equalClass.sort(Comparator.comparing(Expr::toString));
        }
        return res;
    }

    private static boolean overlap(List<Expr> l1, List<Expr> l2) {
        for (Expr e1: l1) {
            for (Expr e2: l2) {
                if (e1.equals(e2))
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<Integer> s1 = Arrays.asList(1,2,3);
        List<Integer> s2 = Arrays.asList(4,5,6);
        List<List<Integer>> l = new ArrayList<>();
        l.add(s1);
        l.add(s2);
        List<List<Integer>> l2 = new ArrayList<>();
        l2.addAll(l);
        System.out.println(l2);
    }


}
