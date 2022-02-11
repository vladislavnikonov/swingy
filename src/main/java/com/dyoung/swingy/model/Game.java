package com.dyoung.swingy.model;

import java.util.Random;

public class Game {
    private static Game instance;
    private Candidate candidate;
    private int[][] map;
    private int size;
    private int x;
    private int y;

    public void init(Candidate candidate) {
        this.candidate = candidate;
        Random random = new Random();
        int level = candidate.getLevel();

        size = (level - 1) * 5 + 10 - (level % 2);
        map = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int num = random.nextInt(10);
                map[i][j] = num;
            }
        }
        x = size / 2;
        y = size / 2;
    }

    public Employer createEmployer(int hr) {
        Random random = new Random();
        int attack = random.nextInt(candidate.getAttack()) + (hr + 5);
        int defense = random.nextInt(candidate.getDefense()) + (hr + 5);
        int hitPoints = random.nextInt(candidate.getHitPoints()) + (hr + 5);

        Artifact artifact = createArtifact(hr);
        return new Employer(attack, defense, hitPoints, artifact);
    }

    private Artifact createArtifact(int hr) {
        Random random = new Random();
        Artifact artifact = null;
        int r = random.nextInt(6);

        if (r == 0)
            artifact = new Artifact("Technology", random.nextInt((hr + 10) * (candidate.getLevel() + 1)) + 1);
        else if (r == 1)
            artifact = new Artifact("Course", random.nextInt((hr + 10) * (candidate.getLevel() + 1)) + 1);
        else if (r == 2)
            artifact = new Artifact("Book", random.nextInt((hr + 10) * (candidate.getLevel() + 1)) + 1);
        return artifact;
    }

    public int interview(Employer employer) {
        Random random = new Random();
        int xp = employer.getAttack() + employer.getDefense() + employer.getHitPoints();
        int points = random.nextInt(xp);
        return offer(employer) ? points * 10 : -1;
    }

    public boolean offer(Employer employer) {
        Random random = new Random();
        while (employer.getHitPoints() > 0 && candidate.getHitPoints() > 0) {
            switch (random.nextInt(3)) {
                case 0:
                case 1:
                    employer.setHitPoints(employer.getHitPoints() - Math.abs(candidate.getAttack() - employer.getDefense()));
                case 2:
                    candidate.setHitPoints(candidate.getHitPoints() - Math.abs(employer.getAttack() - candidate.getDefense()));
            }
        }
        return candidate.getHitPoints() > 0;
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public int[][] getMap() {
        return map;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
