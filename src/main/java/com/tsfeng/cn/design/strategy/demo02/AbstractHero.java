package com.tsfeng.cn.design.strategy.demo02;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 17:09
 */
public abstract class AbstractHero {

    private String heroName;

    /**
     * 所有具体英雄可以自定义的开场白
     */
    public abstract void introduce();

    /**
     * 持有一个具体的策略对象
     */
    private HeroSkillStrategy heroSkillStrategy;

    public AbstractHero(HeroSkillStrategy heroSkillStrategy) {
        this.heroSkillStrategy = heroSkillStrategy;
    }

    /**
     * 改变策略
     * @param heroSkillStrategy
     */
    public void changeStrategy(HeroSkillStrategy heroSkillStrategy) {
        this.heroSkillStrategy = heroSkillStrategy;
    }

    /**
     * 设置英雄的召唤师技能
     */
    public void setHeroSkill() {
        this.heroSkillStrategy.setHeroSkill();
    }


}
