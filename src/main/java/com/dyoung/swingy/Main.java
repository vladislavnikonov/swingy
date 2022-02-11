package com.dyoung.swingy;

import com.dyoung.swingy.controller.MenuController;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui"))) {
            System.out.println("Usage: java -jar target\\swingy-1.0-SNAPSHOT.jar (console | gui)");
            System.exit(1);
        }
        new MenuController().start(args[0]);
    }
}
