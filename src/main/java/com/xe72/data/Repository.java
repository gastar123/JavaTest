package com.xe72.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private ConnectionProvider connectionProvider;

    public Repository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void createTable() throws SQLException {
        try (Connection connection = connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("create table TEST (FIELD int)");
            statement.close();
            System.out.println("create complete");
        }
    }

    public List<String> selectEntries() throws SQLException {
        List<String> fieldList = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from test");
            while (result.next()) {
                fieldList.add(result.getString("field"));
            }
            statement.close();
            System.out.println("select complete");
        }
        return fieldList;
    }

    public void insertEntries(List<Integer> entryList) throws SQLException {
        clearTable();
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into test values (?)");
            connection.setAutoCommit(false);
            for (Integer entry : entryList) {
                statement.setInt(1, entry);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            statement.close();
            System.out.println("insert complete");
        }
    }

    private void clearTable() throws SQLException {
        try (Connection connection = connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("truncate table test");
            statement.close();
        }
    }
}
