package com.xe72.services;

import com.xe72.data.Repository;
import com.xe72.data.XMLUseCase;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private Repository repository;
    private XMLUseCase xmlUseCase;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void setXmlUseCase(XMLUseCase xmlUseCase) {
        this.xmlUseCase = xmlUseCase;
    }

    public void createTableInDB() throws SQLException {
        repository.createTable();
    }

    public void insertEntriesInDB(int rowCount) throws SQLException {
        List<Integer> fieldList = new ArrayList<>();
        for (int i = 1; i <= rowCount; i++) {
            fieldList.add(i);
        }
        repository.insertEntries(fieldList);
    }

    public void createFileWithDataFromDB(String fileName) throws SQLException, FileNotFoundException, XMLStreamException {
        xmlUseCase.createXmlFile(repository.selectEntries(), fileName);
    }

    public void transformFileWithXslt(String oldFileName, String newFileName, String xsltName) throws TransformerException {
        xmlUseCase.transformXML(oldFileName, newFileName, xsltName);
    }

    public void parseXmlAndCalculate(String fileName) throws FileNotFoundException, XMLStreamException {
        long sum = 0;
        for (Integer fieldValue: xmlUseCase.parseXml(fileName)) {
            sum += fieldValue;
        }
        System.out.println("Sum: " + sum);
    }
}
