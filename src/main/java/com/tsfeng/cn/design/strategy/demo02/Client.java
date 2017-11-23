package com.tsfeng.cn.design.strategy.demo02;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 17:35
 */
public class Client {

    public static void main(String[] args) {
        BaseHero houYi = new BaseHero();
        houYi.introduce();
        houYi.setHeroSkill();

        houYi.changeStrategy(new AddHpStrategy());
        houYi.setHeroSkill();
    }
}
