package com.tsfeng.cn.core.javakey;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/27 18:11
 */
public class Book implements Externalizable {

    static String name;
    transient String content;

    // 必须要有一个自定义的有效的构造方法，非默认，否则运行时会报错。
    public Book() {
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(content);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        content = (String) in.readObject();
    }
}
