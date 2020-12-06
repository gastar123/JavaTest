package com.xe72;

import com.xe72.data.ConnectionProvider;
import com.xe72.data.Repository;
import com.xe72.data.XMLUseCase;
import com.xe72.services.Service;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;

public class Application {

    private String url;
    private String user;
    private String password;
    private int rowCount;

    private final String firstFileName = "1.xml";
    private final String secondFileName = "result.xml";
    private final String xsltFileName = "XMLTransformer.xsl";

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void start() {
        Service service = buildService();
        try {
//            Создание таблицы при первом запуске, для теста
//            service.createTableInDB();
            service.insertEntriesInDB(rowCount);
            service.createFileWithDataFromDB(firstFileName);
            service.transformFileWithXslt(firstFileName, secondFileName, xsltFileName);
            service.parseXmlAndCalculate(secondFileName);
        } catch (SQLException | TransformerException | IOException | XMLStreamException exception) {
            exception.printStackTrace();
        }
    }

    private Service buildService() {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        connectionProvider.setUrl(url);
        connectionProvider.setUser(user);
        connectionProvider.setPassword(password);

        Repository repository = new Repository(connectionProvider);
        XMLUseCase xmlUseCase = new XMLUseCase();
        Service service = new Service();
        service.setRepository(repository);
        service.setXmlUseCase(xmlUseCase);
        return service;
    }
}
