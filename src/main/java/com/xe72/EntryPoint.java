package com.xe72;

import java.util.Scanner;

public class EntryPoint {

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        Application application = new Application();
        System.out.println("Entry count: ");
        Scanner scanner = new Scanner(System.in);

        application.setUrl("jdbc:h2:file:~/data-test-magnit");
        application.setUser("sa");
        application.setPassword("sa");
        application.setRowCount(scanner.nextInt());

        application.start();
    }
}
