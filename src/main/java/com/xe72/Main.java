package com.xe72;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DBUtils dbUtils = new DBUtils();
        XMLUtils xmlUtils = new XMLUtils();
        System.out.println("Entry count: ");
        Scanner scanner = new Scanner(System.in);

        dbUtils.setUrl("jdbc:postgresql://localhost:5432/");
        dbUtils.setUser("postgres");
        dbUtils.setPassword("12345");
        dbUtils.setRowCount(scanner.nextInt());

        try {
            List<String> fieldList = dbUtils.connectToDB();
            xmlUtils.writeXML(fieldList);
            xmlUtils.transformXML();
            xmlUtils.parseXML();
        } catch (SQLException | ParserConfigurationException | TransformerException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }
}
