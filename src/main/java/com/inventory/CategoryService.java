package com.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CategoryService {
    private final Connection conn;

    public CategoryService(Connection conn) {
        this.conn = conn;
    }

    // View all categories
    public void viewCategories() {
        String query = "SELECT * FROM categories";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nCategories:");
            System.out.printf("%-5s %-20s\n", "ID", "Category");
            System.out.println("----------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-20s\n", rs.getInt("id"), rs.getString("category_name"));
            }
            System.out.println("\n");
        } catch (SQLException e) {
            System.out.println("❌ Error viewing categories: " + e.getMessage());
        }
    }

    // Add a new category
    public void addCategory(Scanner scanner) {
        System.out.print("Enter new category name: ");
        String categoryName = scanner.nextLine();

        String query = "INSERT INTO categories (category_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, categoryName);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Category added successfully!");
            } else {
                System.out.println("❌ Failed to add category.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error adding category: " + e.getMessage());
        }
    }

    public Object updateCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCategory'");
    }
}
