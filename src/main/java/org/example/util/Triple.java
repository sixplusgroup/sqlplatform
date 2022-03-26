package org.example.util;

/**
 * @author shenyichen
 * @date 2022/3/26
 **/
public class Triple<A, B, C> {
    public A first;
    public B second;
    public C third;

    public Triple(A a, B b, C c) {
        first = a;
        second = b;
        third = c;
    }
}
