package com.dyoung.swingy.view.console;

import com.dyoung.swingy.controller.GameController;
import com.dyoung.swingy.controller.MenuController;
import com.dyoung.swingy.model.Game;
import com.dyoung.swingy.util.DataBase;

import java.util.Scanner;

public class GameConsole {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String VIEW = "console";
    private final GameController controller;

    public GameConsole() {
        controller = GameController.getInstance();
    }

    public void start() {
        controller.update(VIEW);
    }

    public void update(Game game) {
        System.out.println(game.getCandidate().toString());
        controller.printMap(VIEW);
        System.out.println("\nEnter the command:");
        System.out.println("w - to go north");
        System.out.println("a - to go west");
        System.out.println("s - to go south");
        System.out.println("d - to go east");
        System.out.println("gui - to switch to GUI");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("w".equals(input) || "a".equals(input) || "d".equals(input) || "s".equals(input)) {
                controller.move(input, VIEW);
                break;
            } else if ("gui".equals(input)) {
                controller.chooseSwitch(VIEW);
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    public void printMap(int[][] map, int x, int y) {
        System.out.printf("%dX%d", map.length, map.length);
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (x == j && y == i)
                    System.out.print(ANSI_PURPLE + "H " + ANSI_RESET);
                else {
                    switch (map[i][j]) {
                        case 0:
                            System.out.print(ANSI_YELLOW + "T " + ANSI_RESET);
                            break;
                        case 1:
                            System.out.print(ANSI_GREEN + "S " + ANSI_RESET);
                            break;
                        case 2:
                            System.out.print(ANSI_BLUE + "M " + ANSI_RESET);
                            break;
                        case 3:
                            System.out.print(ANSI_RED + "Y " + ANSI_RESET);
                            break;
                        default:
                            System.out.print(". ");
                    }
                }
            }
            System.out.println();
        }
    }

    public void overGame() {
        controller.overGame();
    }

    public void finish() {
        MenuController.getFrame().dispose();
        DataBase.close();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void invitedInterview(int hr) {
        System.out.println("You were invited for an interview\n");
        System.out.println("Enter the command:");
        System.out.println("go - to fight with the employer");
        System.out.println("refuse - to run away from the employer");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("go".equals(input)) {
                controller.chooseGo(hr, VIEW);
                break;
            } else if ("refuse".equals(input)) {
                controller.chooseRefuse(hr, VIEW);
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    public boolean keepArtifact(String replaceMessage) {
        System.out.println(replaceMessage);
        System.out.println("\nEnter the command:");
        System.out.println("keep - to keep/change this artifact");
        System.out.println("leave - to leave this artifact");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if ("keep".equals(input)) {
                return true;
            } else if ("leave".equals(input)) {
                return false;
            } else {
                System.out.println("Unknown command");
            }
        }
        return false;
    }

    public void winGame() {
        System.out.println("YOU WON");
        System.out.println();
        System.out.println("Enter the command:");
        System.out.println("continue - to continue the game");
        System.out.println("exit - to exit the game");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("continue".equals(input)) {
                controller.chooseContinue(VIEW);
                break;
            } else if ("exit".equals(input)) {
                controller.chooseExit(VIEW);
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    public void switchView(int flag) {
        if (flag == 1) {
            MenuController.showFrame();
            controller.update("gui");
        } else
            controller.start("gui");
    }
}
