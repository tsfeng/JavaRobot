package com.tsfeng.cn.design.strategy.demo02;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 17:35
 */
public class Client {
    public static void main(String[] args) {
        姜子牙 jzy = new 姜子牙();
        jzy.introduce();
        System.out.print("更换召唤师技能之前：");
        jzy.setHeroSkill();
        System.out.print("更换召唤师技能之后：");
        jzy.changeStrategy(new AddHpStrategy());
        jzy.setHeroSkill();
    }
}
