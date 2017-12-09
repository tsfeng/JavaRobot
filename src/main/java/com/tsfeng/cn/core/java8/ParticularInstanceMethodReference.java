package com.tsfeng.cn.core.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/9 21:08
 * 引用特定对象的实例方法
 */
public class ParticularInstanceMethodReference {

    public static void main(String args[]) {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        final MyComparator myComparator = new MyComparator();
        // 方法引用
        Collections.sort(list, myComparator::compare);
        // Lambda表达式
        Collections.sort(list, (a, b) -> myComparator.compare(a, b));
    }

    private static class MyComparator {
        public int compare(final Integer a, final Integer b) {
            return a.compareTo(b);
        }
    }
}
