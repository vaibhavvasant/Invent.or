package com.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        // Establishing database connection
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorydb", "user", "User@123");

            // Creating MainMenu instance and passing the connection
            MainMenu menu = new MainMenu(conn);
            menu.showMenu(); // Start the menu
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
        }
    }
}
