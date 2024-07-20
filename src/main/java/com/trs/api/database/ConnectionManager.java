package com.trs.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Class specifically for connecting to the database
 */
public class ConnectionManager {
    private final static String URL = "jdbc:mariadb://localhost:3306/trs";
    private final static String DRIVER_NAME = "org.mariadb.jdbc.Driver";
    private final static String USER = "zodic";
    private final static String PASSWORD = "root";

    // Method to get the connection
    public static Connection initDatabaseConnection() throws SQLException {
        Connection con;
        System.out.println("Connecting to the database...");
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection valid: " + con.isValid(5));
        return con;
    }

    // Method to end the connection
    public static void closeDatabaseConnection(Connection con) throws SQLException {
        System.out.println("Closing database connection...");
        con.close();
        System.out.println("Connection valid: " + con.isValid(5));
    }

    // Method to test the connection
    public static Boolean canConnect(Connection con) throws SQLException {
        try {
            initDatabaseConnection();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            if (con != null) {
                closeDatabaseConnection(con);
            }
        }
    }
}