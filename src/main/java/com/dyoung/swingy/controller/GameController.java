package com.dyoung.swingy.controller;

import com.dyoung.swingy.model.Artifact;
import com.dyoung.swingy.model.Candidate;
import com.dyoung.swingy.model.Employer;
import com.dyoung.swingy.model.Game;
import com.dyoung.swingy.util.DataBase;
import com.dyoung.swingy.view.console.GameConsole;
import com.dyoung.swingy.view.gui.GameGUI;

import java.util.Random;

public class GameController {
    private static GameController instance;
    private static final GameGUI gui = new GameGUI();
    private static final GameConsole console = new GameConsole();
    private final Game game;
    private static int flag = 0;

    public GameController() {
        game = Game.getInstance();
    }

    public void start(String arg) {
        if (arg.equals("console"))
            console.start();
        else if (arg.equals("gui")) {
            flag = 1;
            gui.start();
        }
    }

    public void update(String arg) {
        if (arg.equals("console"))
            console.update(game);
        else if (arg.equals("gui"))
            gui.update(game);
    }

    public void printMap(String arg) {
        if (arg.equals("console"))
            console.printMap(game.getMap(), game.getX(), game.getY());
        else if (arg.equals("gui"))
            gui.printMap(game.getMap(), game.getX(), game.getY());
    }

    public void move(String direction, String arg) {
        int x = game.getX();
        int y = game.getY();

        switch (direction) {
            case "w":
                y--;
                break;
            case "a":
                x--;
                break;
            case "s":
                y++;
                break;
            case "d":
                x++;
                break;
        }

        if (x < 0 || y < 0 || x >= game.getSize() || y >= game.getSize()) {
            if (arg.equals("console"))
                console.winGame();
            else if (arg.equals("gui"))
                gui.winGame();
            return;
        }

        game.setX(x);
        game.setY(y);

        if (game.getMap()[y][x] < 4) {
            if (arg.equals("console"))
                console.invitedInterview(game.getMap()[y][x]);
            else if (arg.equals("gui"))
                gui.invitedInterview(game.getMap()[y][x]);
        }

        if (game.getCandidate().getHitPoints() > 0) {
            if (arg.equals("console"))
                console.update(game);
            else if (arg.equals("gui"))
                gui.update(game);
        }
    }

    public void overGame() {
        Candidate candidate = game.getCandidate();
        DataBase.deleteCandidate(candidate);
    }

    private void updateDataBase() {
        Candidate candidate = game.getCandidate();
        DataBase.updateCandidate(candidate);
    }

    public void chooseRefuse(int hr, String arg) {
        if (new Random().nextBoolean()) {
            if (arg.equals("console"))
                console.showMessage("You were lucky and were able to escape");
            else if (arg.equals("gui"))
                gui.showMessage("You were lucky and were able to escape");
            game.getMap()[game.getY()][game.getX()] = 4;
        } else {
            if (arg.equals("console"))
                console.showMessage("You were unlucky\nMUST FIGHT");
            else if (arg.equals("gui"))
                gui.showMessage("You were unlucky\nMUST FIGHT");
            chooseGo(hr, arg);
        }
    }

    public void chooseGo(int hr, String arg) {
        Employer employer = game.createEmployer(hr);
        int xp = game.interview(employer);

        if (xp > -1) {
            if (arg.equals("console"))
                console.showMessage("You passed and got " + xp + "XP");
            else if (arg.equals("gui"))
                gui.showMessage("You passed and got " + xp + "XP");
            game.getMap()[game.getY()][game.getX()] = 4;
            setArtifact(employer.getArtifact(), arg);
            int level = game.getCandidate().getLevel();
            game.getCandidate().setExperience(xp);
            if (level != game.getCandidate().getLevel()) {
                if (arg.equals("console"))
                    console.showMessage("LEVEL UP\n");
                else if (arg.equals("gui"))
                    gui.showMessage("LEVEL UP\n");
                game.init(game.getCandidate());
            }
        } else {
            if (arg.equals("console")) {
                console.showMessage("You didn't pass\nGAME OVER");
                console.overGame();
                console.finish();
            } else if (arg.equals("gui")) {
                gui.showMessage("You didn't pass\nGAME OVER");
                gui.overGame();
                gui.finish();
            }
        }
    }

    private void setArtifact(Artifact artifact, String arg) {
        if (artifact != null) {
            boolean choose = false;
            if (arg.equals("console"))
                choose = console.keepArtifact("You got " + artifact);
            else if (arg.equals("gui"))
                choose = gui.keepArtifact("You got " + artifact);
            if (choose) {
                switch (artifact.getName()) {
                    case "Technology":
                        game.getCandidate().setWeapon(artifact);
                        break;
                    case "Course":
                        game.getCandidate().setArmor(artifact);
                        break;
                    case "Book":
                        game.getCandidate().setHelm(artifact);
                        break;
                }
            }
        }
    }

    public void chooseContinue(String arg) {
        updateDataBase();
        game.init(game.getCandidate());
        if (arg.equals("console"))
            console.update(game);
        else if (arg.equals("gui"))
            gui.update(game);
    }

    public void chooseExit(String arg) {
        updateDataBase();
        if (arg.equals("console"))
            console.finish();
        else if (arg.equals("gui"))
            gui.finish();
    }

    public void chooseSwitch(String arg) {
        if (arg.equals("console"))
            console.switchView(flag);
        else if (arg.equals("gui"))
            gui.switchView();
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public Game getGame() {
        return game;
    }
}
