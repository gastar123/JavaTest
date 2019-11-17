package com.xe72;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DBUtils dbUtils = new DBUtils();
        XMLUtils xmlUtils = new XMLUtils();
        System.out.println("Enter number");
        Scanner scanner = new Scanner(System.in);

        dbUtils.setUrl("jdbc:mysql://127.0.0.1:3306/note2");
        dbUtils.setUser("notes_user");
        dbUtils.setPassword("123");
        dbUtils.setRowCount(scanner.nextInt());

        List<String> fieldList = dbUtils.connectToDB();
        xmlUtils.writeXML(fieldList);
        xmlUtils.transformXML();
        xmlUtils.parseXML();
    }
}
