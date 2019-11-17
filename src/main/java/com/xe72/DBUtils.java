package com.xe72;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    private String url;
    private String user;
    private String password;
    private int rowCount;

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

    public List<String> connectToDB() throws SQLException {
        List<String> fieldList = new ArrayList<>();
        try (Connection connect = DriverManager.getConnection(url, user, password)) {
            Statement clearTable = connect.createStatement();
            clearTable.execute("truncate table test");

            PreparedStatement insertEntry = connect.prepareStatement("insert into test values (?)");
            connect.setAutoCommit(false);
            for (int i = 1; i <= rowCount; i++) {
                insertEntry.setInt(1, i);
                insertEntry.addBatch();
            }
            int[] batch = insertEntry.executeBatch();
            connect.commit();
            System.out.println("insert complete");

            Statement getEntries = connect.createStatement();
            ResultSet result = getEntries.executeQuery("select * from test");
            while (result.next()) {
                fieldList.add(result.getString("field"));
            }

            System.out.println("select complete");
        }

        return fieldList;
    }
}
