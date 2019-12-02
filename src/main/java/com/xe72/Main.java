package com.xe72;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        XMLUtils xmlUtils = new XMLUtils();
        DBUtils dbUtils = new DBUtils(xmlUtils);
        System.out.println("Entry count: ");
        Scanner scanner = new Scanner(System.in);

        dbUtils.setUrl("jdbc:postgresql://localhost:5432/");
        dbUtils.setUser("postgres");
        dbUtils.setPassword("12345");
        dbUtils.setRowCount(scanner.nextInt());

        try {
            dbUtils.connectToDB();
            xmlUtils.transformXML();
            xmlUtils.parseXML();
        } catch (SQLException | TransformerException | IOException | XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
