package com.dyoung.swingy.model;

import com.dyoung.swingy.util.UniqueKey;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Candidate {
    private int id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 15, message = "Size must be between 3 and 15 symbols")
    @UniqueKey()
    private final String heroName;

    private final String heroClass;
    private int level;
    private int experience;
    private int attack;
    private int defense;
    private int hitPoints;
    private Artifact weapon;
    private Artifact armor;
    private Artifact helm;

    public Candidate(int id, String heroName, String heroClass, int level, int experience, int attack,
                     int defense, int hitPoints, Artifact weapon, Artifact armor, Artifact helm) {
        this.id = id;
        this.heroName = heroName;
        this.heroClass = heroClass;
        this.level = level;
        this.experience = experience;
        this.attack = attack;
        this.defense = defense;
        this.hitPoints = hitPoints;
        this.weapon = weapon;
        this.armor = armor;
        this.helm = helm;
    }

    public int getNextLevel() {
        return (level + 1) * 1000 + level * level * 450;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeroName() {
        return heroName;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        int nextLevel = getNextLevel();

        if (this.experience + experience >= nextLevel) {
            level++;
            attack += level * 10;
            defense += level * 10;
            hitPoints += level * 10;
        }
        this.experience += experience;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Artifact getWeapon() {
        return weapon;
    }

    public void setWeapon(Artifact weapon) {
        if (this.weapon != null) {
            this.attack -= this.weapon.getPoints();
        }
        this.attack += weapon.getPoints();
        this.weapon = weapon;
    }

    public Artifact getArmor() {
        return armor;
    }

    public void setArmor(Artifact armor) {
        if (this.armor != null) {
            this.defense -= this.armor.getPoints();
        }
        this.defense += armor.getPoints();
        this.armor = armor;
    }

    public Artifact getHelm() {
        return helm;
    }

    public void setHelm(Artifact helm) {
        if (this.helm != null) {
            this.hitPoints -= this.helm.getPoints();
            if (this.hitPoints + helm.getPoints() <= 0) {
                this.hitPoints += this.helm.getPoints();
                return;
            }
        }
        this.hitPoints += helm.getPoints();
        this.helm = helm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(heroName).append("\n");
        sb.append("Class: ").append(heroClass).append("\n");
        sb.append("Level: ").append(level).append("\n");
        sb.append("Experience: ").append(experience).append(" / ").append(getNextLevel()).append("\n");
        sb.append("Attack: ").append(attack).append("\n");
        sb.append("Defense: ").append(defense).append("\n");
        sb.append("Hit Points: ").append(hitPoints).append("\n");
        sb.append("Technology: ");
        if (weapon != null)
            sb.append("+").append(weapon.getPoints()).append(" to attack").append("\n");
        else
            sb.append(" -\n");
        sb.append("Course: ");
        if (armor != null)
            sb.append("+").append(armor.getPoints()).append(" to defense").append("\n");
        else
            sb.append(" -\n");
        sb.append("Book: ");
        if (helm != null)
            sb.append("+").append(helm.getPoints()).append(" to hit points").append("\n");
        else
            sb.append(" -\n");
        return sb.toString();
    }
}
