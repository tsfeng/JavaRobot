package com.tsfeng.cn.core.java8;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/5 13:51
 * 3、函数式接口
 */
public class Java8Demo03 {

    public static void main(String args[]){

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("run1...");
            }
        };

        Runnable r2 = () -> System.out.println("run2...");
//
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
//
//        // Predicate<Integer> predicate = n -> true
//        // n 是一个参数传递到 Predicate 接口的 test 方法
//        // n 如果存在则 test 方法返回 true
//
//        System.out.println("输出所有数据:");
//        // 传递参数 n
//        eval(list, n->true);
//
//        // Predicate<Integer> predicate1 = n -> n%2 == 0
//        // n 是一个参数传递到 Predicate 接口的 test 方法
//        // 如果 n%2 为 0 test 方法返回 true
//
//        System.out.println("输出所有偶数:");
//        eval(list, n-> n%2 == 0 );
//
//        // Predicate<Integer> predicate2 = n -> n > 3
//        // n 是一个参数传递到 Predicate 接口的 test 方法
//        // 如果 n 大于 3 test 方法返回 true
//
//        System.out.println("输出大于 3 的所有数字:");
//        eval(list, n-> n > 3 );
    }

//    public static void eval(List<Integer> list, Predicate<Integer> predicate) {
//        for(Integer n: list) {
//            if(predicate.test(n)) {
//                System.out.println(n + " ");
//            }
//        }
//    }
}


//@FunctionalInterface
//interface FunctionInterfaceTest{
//    void f1();
//
//    String toString();
//}