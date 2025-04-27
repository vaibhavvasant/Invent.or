package com.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/inventorydb"; // Adjust as necessary
    private static final String USER = "user"; // Adjust with your username
    private static final String PASSWORD = "User@123"; // Adjust with your password

    public static Connection getConnection() {
        try {
            // Try to establish a connection to the database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Error connecting to database: " + e.getMessage());
            return null;
        }
    }
}
