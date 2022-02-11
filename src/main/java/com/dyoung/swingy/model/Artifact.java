package com.dyoung.swingy.model;

public class Artifact {
    private final String name;
    private final int points;

    public Artifact(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return name + " (+" + points + ")";
    }
}
