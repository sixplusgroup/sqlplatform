package org.example;

import org.example.node.condition.*;
import org.example.node.expr.Expr;
import org.example.node.expr.FuncExpr;
import org.example.node.expr.ListExpr;
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

    // todo 规范化
    public static void canonicalize(Select ast) {
        if (ast instanceof SetOpSelect) {
            canonicalize(((SetOpSelect) ast).left);
            canonicalize(((SetOpSelect) ast).right);
        } else if (ast instanceof PlainSelect) {
            PlainSelect select = (PlainSelect) ast;
            substituteEqualClass(select);

        }
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
            for (Condition subCond: ((CompoundCond) c).getSubConds()) {
                substituteCondition(equalClasses, subCond);
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
