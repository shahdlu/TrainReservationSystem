package com.trs.api.database;

import javax.xml.transform.Source;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class CRUD {
    private static ResultSet RS;
    private static PreparedStatement PS;
    private static boolean CONNECTED = false;
    private static Connection connection;

    //Connect to the database at instantiation
    static {
        try {
            connection = ConnectionManager.initDatabaseConnection();
            System.out.println("Connection Successful");
            CONNECTED = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }

    /*** Utility Methods ***/

    //Check if connection was made successfully or not
    protected static boolean isConnected() {
        return CONNECTED;
    }

    //Utility method to return all values in a table
    protected static ResultSet getAll(String table) throws SQLException {
        if (isConnected()) {
            PS = connection.prepareStatement("SELECT * FROM " + table);
            RS = PS.executeQuery();
            return RS;
        }
        return null;
    }

    //Utility method to return all values in a table with a condition
    protected static ResultSet getAll(String table, String condition) throws SQLException {
        if (isConnected()) {
            PS = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + condition);
            RS = PS.executeQuery();
            return RS;
        }
        return null;
    }

    //Utility method to return a single value from a table
    protected static ResultSet get(String table, String column, String condition) throws SQLException {
        if (isConnected()) {
            String sql = "SELECT " + column + " FROM " + table + " WHERE " + condition;
            PS = connection.prepareStatement(sql);
            System.out.println(sql);
            RS = PS.executeQuery();
            return RS;
        }
        return null;
    }

    //Utility method to insert values into a table
    protected static void insert(String tableName, List<String> columns, List<String> values) throws SQLException {
        if (isConnected()) {
            String sql = "INSERT INTO " + tableName + " (" + String.join(", ", columns) + ") VALUES (" + String.join(", ", values) + ")";

            System.out.println(sql);
            PS = connection.prepareStatement(sql);
            PS.executeUpdate();
        }
    }

    //Utility method to update a row in a table
    protected static void update(String tableName, List<String> columns, List<String> values, String condition) throws SQLException {
        if (isConnected()) {
            String sql = "UPDATE " + tableName + " SET ";
            for (int i = 0; i < columns.size() - 1; i++) {
                sql += columns.get(i) + " = " + values.get(i);
                if (i != columns.size() - 2) {
                    sql += ", ";
                }
            }
            sql += " WHERE " + condition;
            System.out.println(sql);
            PS = connection.prepareStatement(sql);
            PS.executeUpdate();
        }
    }


    //Utility method to update a single in a table where a condition is met
    protected static void update(String tableName, String columns, String values, String condition) throws SQLException {
        if (isConnected()) {
            String sql = "UPDATE " + tableName + " SET " + String.join(", ", columns) + " = " + String.join(", ", values) + " WHERE " + condition;
            System.out.println(sql);
            PS = connection.prepareStatement(sql);
            PS.executeUpdate();
        }
    }

    //Utility method to delete everything from the database
    protected static void prune(String tableName) throws SQLException {
        if (isConnected()) {
            PS = connection.prepareStatement("DELETE FROM " + tableName);
            PS.executeUpdate();
        }
    }

    //a utility method to delete a row from a table where a condition is met
    protected static void deleteWhereEqual(String tableName, String column, String value) throws SQLException {
        if (isConnected()) {
            String sql = "DELETE FROM " + tableName + " WHERE " + column + " = " + value;
            System.out.println(sql);
            PS = connection.prepareStatement(sql);
            PS.executeUpdate();
        }
    }

    protected static void deleteWhereLessThan(String tableName, String column, String value) throws SQLException {
        if (isConnected()) {
            String sql = "DELETE FROM " + tableName + " WHERE " + column + " < " + value;
            System.out.println(sql);
            PS = connection.prepareStatement(sql);
            PS.executeUpdate();
        }
    }


}
