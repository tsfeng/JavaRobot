package com.tsfeng.cn.effective;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/24 10:00
 */
public class 中文Demo {

    public static void main(String[] args) {
        int 张三的年龄 = 20;
        int 李四的年龄 = 21;
        boolean 张三的年龄是不是比李四的年龄要大 = 判断甲的年龄大还是乙大(张三的年龄, 李四的年龄);
        if (张三的年龄是不是比李四的年龄要大 == 对) {
            在控制台输出这么一句话并换行("张三的年龄大");
        } else if (张三的年龄是不是比李四的年龄要大 == 大错特错) {
            在控制台输出这么一句话并换行("李四的年龄大");
        }
    }

    /**
     * --------------定义自然语言(把你的注释删掉,改成方法名)--------------
     */
    static boolean 对 = true;
    static boolean 大错特错 = false;

    /**
     * 你确定我应该写注释?
     */
    public static boolean 判断甲的年龄大还是乙大(int 甲的年龄, int 乙的年龄) {
        return 甲的年龄 > 乙的年龄;
    }

    public static void 在控制台输出这么一句话并换行(String 要在控制台输出的话) {
        System.out.println(要在控制台输出的话);
    }
}
