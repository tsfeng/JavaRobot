package com.tsfeng.cn.design.strategy.demo02;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 17:27
 */
public class BaseHero extends AbstractHero {

    public BaseHero(){
        super(new RunFastStrategy());
    }

    @Override
    public void introduce() {
        System.out.println("默认开场白");
    }
}
