package com.dyoung.swingy.view.console;

import com.dyoung.swingy.controller.MenuController;

import java.util.Scanner;

public class StartConsole {
    public static final String VIEW = "console";
    private final MenuController controller;

    public StartConsole() {
        controller = MenuController.getInstance();
    }

    public void start() {
        System.out.println("Welcome to Swingy!");
        System.out.println("\nEnter the command:");
        System.out.println("create - to create candidate");
        System.out.println("select - to select candidate");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if ("create".equals(input)) {
                controller.create(VIEW);
                break;
            } else if ("select".equals(input)) {
                controller.select(VIEW);
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }
}
