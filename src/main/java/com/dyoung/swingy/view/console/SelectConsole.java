package com.dyoung.swingy.view.console;

import com.dyoung.swingy.controller.MenuController;

import java.util.Objects;
import java.util.Scanner;

public class SelectConsole {
    public static final String VIEW = "console";
    private final MenuController controller;

    public SelectConsole() {
        controller = MenuController.getInstance();
    }

    public void start() {
        System.out.println("Available candidates: ");
        showCandidates(controller.getListData());
        System.out.println("\nEnter the command:");
        System.out.println("<name> - enter name of available candidate to start the game");
        System.out.println("create - to create candidate");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if ("create".equals(input)) {
                controller.create(VIEW);
                break;
            } else if (isValid(input, controller.getListData())) {
                controller.selectCandidate(input, VIEW);
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    private void showCandidates(String[] heroes) {
        if (heroes.length == 0)
            System.out.println("The list is empty");
        for (String hero : heroes) {
            System.out.println(hero);
        }
    }

    private boolean isValid(String str, String[] max) {
        for (String hero : max) {
            if (Objects.equals(hero, str))
                return true;
        }
        return false;
    }
}
