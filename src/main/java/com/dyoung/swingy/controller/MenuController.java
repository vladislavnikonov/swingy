package com.dyoung.swingy.controller;

import com.dyoung.swingy.model.Candidate;
import com.dyoung.swingy.model.Game;
import com.dyoung.swingy.util.CandidateBuilder;
import com.dyoung.swingy.util.DataBase;
import com.dyoung.swingy.util.Director;
import com.dyoung.swingy.view.console.CreateConsole;
import com.dyoung.swingy.view.console.SelectConsole;
import com.dyoung.swingy.view.console.StartConsole;
import com.dyoung.swingy.view.gui.CreateGUI;
import com.dyoung.swingy.view.gui.SelectGUI;
import com.dyoung.swingy.view.gui.StartGUI;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;

public class MenuController {
    private static MenuController instance;
    private static JFrame frame;
    private final Game game;

    public MenuController() {
        game = Game.getInstance();
    }

    public void start(String arg) {
        DataBase.connect();
        if (arg.equals("console"))
            new StartConsole().start();
        else if (arg.equals("gui"))
            new StartGUI().start();
    }

    public void create(String arg) {
        if (arg.equals("console"))
            new CreateConsole().start();
        else if (arg.equals("gui"))
            new CreateGUI().start();
    }

    public void select(String arg) {
        if (arg.equals("console"))
            new SelectConsole().start();
        else if (arg.equals("gui"))
            new SelectGUI().start();
    }

    public void createCandidate(String name, String heroClass, String arg) {
        Director director = new Director();
        CandidateBuilder builder = new CandidateBuilder(name);
        Candidate candidate;
        try {
            switch (heroClass) {
                case "DevOps":
                    director.createDevOps(builder);
                    break;
                case "BackEnd":
                    director.createBackEnd(builder);
                    break;
                case "FrontEnd":
                    director.createFrontEnd(builder);
                    break;
                case "MobileDev":
                    director.createMobileDev(builder);
                    break;
                case "DataScience":
                    director.createDataScience(builder);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid class: " + heroClass);
            }
            candidate = builder.getResult();
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Candidate>> constrs = validator.validate(candidate);
            for (ConstraintViolation<Candidate> constr : constrs) {
                String stringBuilder = "property: {" +
                        constr.getPropertyPath() +
                        "} value: {" +
                        constr.getInvalidValue() +
                        "} message: {" +
                        constr.getMessage() +
                        "}\n";
                throw new IllegalArgumentException(stringBuilder);
            }
        } catch (IllegalArgumentException e) {
            if (arg.equals("console")) {
                System.out.println(e.getMessage());
                new CreateConsole().start();
            } else if (arg.equals("gui"))
                JOptionPane.showMessageDialog(getFrame(), e.getMessage());
            return;
        }

        int id = DataBase.insertCandidate(candidate.getHeroName(), candidate.getHeroClass(), candidate.getLevel(),
                candidate.getExperience(), candidate.getAttack(), candidate.getDefense(), candidate.getHitPoints());
        candidate.setId(id);
        game.init(candidate);
        new GameController().start(arg);
    }

    public String[] getListData() {
        ArrayList<String> list = DataBase.selectNames();
        String[] arr = new String[list.size()];
        return list.toArray(arr);
    }

    public void selectCandidate(String name, String arg) {
        Candidate candidate = DataBase.selectCandidateByName(name);
        game.init(candidate);
        new GameController().start(arg);
    }

    public static MenuController getInstance() {
        if (instance == null)
            instance = new MenuController();
        return instance;
    }

    public static JFrame getFrame() {
        if (frame == null) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);
            frame.setLocation(450, 100);
            frame.setResizable(true);
            frameListener();
        }
        return frame;
    }

    public static void showFrame() {
        if (frame != null)
            frame.setVisible(true);
    }

    public static void hideFrame() {
        if (frame != null)
            frame.setVisible(false);
    }

    private static void frameListener() {
        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataBase.close();
                super.windowClosing(e);
            }
        });
    }
}
