package com.tsfeng.cn.design.strategy.demo02;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 16:41
 */
public class MoveVeryVeryFastStrategy implements HeroSkillStrategy {
    @Override
    public void setHeroSkill() {
        System.out.println("使用闪现");
    }
}
