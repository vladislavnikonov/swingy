package com.dyoung.swingy.model;

public class Employer {
    private final int attack;
    private final int defense;
    private int hitPoints;
    private final Artifact artifact;

    public Employer(int attack, int defense, int hitPoints, Artifact artifact) {
        this.attack = attack;
        this.defense = defense;
        this.hitPoints = hitPoints;
        this.artifact = artifact;
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

    public Artifact getArtifact() {
        return artifact;
    }
}
