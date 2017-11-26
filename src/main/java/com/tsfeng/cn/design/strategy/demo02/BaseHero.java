package com.tsfeng.cn.design.strategy.demo02;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 17:09
 */
public abstract class BaseHero {

    /**
     * 所有具体英雄都有一个名字
     */
    private String heroName;

    /**
     * 持有一个具体的策略对象
     */
    private HeroSkillStrategy heroSkillStrategy;

    public BaseHero(HeroSkillStrategy heroSkillStrategy) {
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

    /**
     * 所有具体英雄可以自定义的开场白
     */
    public abstract void introduce();

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
