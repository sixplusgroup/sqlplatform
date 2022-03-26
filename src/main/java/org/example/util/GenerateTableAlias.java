package org.example.util;

/**
 * @author shenyichen
 * @date 2022/3/26
 **/
public class GenerateTableAlias {
    static int num = 10;

    public static String getAlias() {
        num++;
        return "t" + num;
    }
}
