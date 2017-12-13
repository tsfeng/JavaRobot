package com.tsfeng.cn.core.diff;

import org.junit.Test;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/13 22:02
 */
public class ClassNotFoundExceptionDemo {

    @Test
    public void test() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
}
