package com.dyoung.swingy.util;

public class Director {

    public void createDevOps(CandidateBuilder builder) {
        builder.setHeroClass("DevOps");
        builder.setAttack(35);
        builder.setDefense(30);
        builder.setHitPoints(85);
    }

    public void createBackEnd(CandidateBuilder builder) {
        builder.setHeroClass("BackEnd");
        builder.setAttack(50);
        builder.setDefense(25);
        builder.setHitPoints(70);
    }

    public void createFrontEnd(CandidateBuilder builder) {
        builder.setHeroClass("FrontEnd");
        builder.setAttack(45);
        builder.setDefense(15);
        builder.setHitPoints(85);
    }

    public void createMobileDev(CandidateBuilder builder) {
        builder.setHeroClass("MobileDev");
        builder.setAttack(55);
        builder.setDefense(20);
        builder.setHitPoints(75);
    }

    public void createDataScience(CandidateBuilder builder) {
        builder.setHeroClass("DataScience");
        builder.setAttack(40);
        builder.setDefense(30);
        builder.setHitPoints(90);
    }
}
