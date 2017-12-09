package com.tsfeng.cn.core.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/9 20:43
 */
public class ConstructorMethodReference {
    public static void main(String args[]) {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        //方法引用
        copyElements(list, ArrayList::new);
        //Lambda表达式
        copyElements(list, () -> new ArrayList<>());
    }

    private static void copyElements(final List<Integer> list, final Supplier<Collection<Integer>> targetCollection) {
        list.forEach(targetCollection.get()::add);
    }
}
