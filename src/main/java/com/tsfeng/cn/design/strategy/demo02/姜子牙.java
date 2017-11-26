package com.tsfeng.cn.design.strategy.demo02;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 17:27
 */
public class 姜子牙 extends BaseHero {

    public 姜子牙(){
        super(new RunFastStrategy());
        super.setHeroName("姜子牙");
        System.out.println("我是姜子牙");
    }

    @Override
    public void introduce() {
        System.out.println("破坏，是为了更伟大的创造。");
    }
}
