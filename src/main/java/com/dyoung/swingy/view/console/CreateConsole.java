package com.dyoung.swingy.view.console;

import com.dyoung.swingy.controller.MenuController;

import java.util.Scanner;

public class CreateConsole {
    public static final String VIEW = "console";
    private final MenuController controller;

    public CreateConsole() {
        controller = MenuController.getInstance();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Class             Attack  Defense   Hit Points\n" +
                "DevOps              35      30      85\n" +
                "BackEnd             50      25      70\n" +
                "FrontEnd            45      15      85\n" +
                "MobileDev           55      20      75\n" +
                "DataScience         40      30      90\n");
        System.out.println("Enter the class:");
        String heroClass = scanner.nextLine();
        controller.createCandidate(name, heroClass, VIEW);
    }
}
