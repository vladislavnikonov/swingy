package com.dyoung.swingy.util;

import com.dyoung.swingy.model.Artifact;
import com.dyoung.swingy.model.Candidate;

public class CandidateBuilder {
    private int id;
    private String heroName;
    private String heroClass;
    private int level;
    private int experience;
    private int attack;
    private int defense;
    private int hitPoints;
    private Artifact weapon;
    private Artifact armor;
    private Artifact helm;

    public CandidateBuilder() {
    }

    public CandidateBuilder(String name) {
        this.setHeroName(name);
        this.setLevel(0);
        this.setExperience(0);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setWeapon(Artifact weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Artifact armor) {
        this.armor = armor;
    }

    public void setHelm(Artifact helm) {
        this.helm = helm;
    }

    public Candidate getResult() {
        return new Candidate(id, heroName, heroClass, level, experience, attack, defense, hitPoints, weapon, armor, helm);
    }
}
