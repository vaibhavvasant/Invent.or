package com.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ProductService {
    private Connection conn;

    public ProductService() {
        this.conn = Database.getConnection(); // Ensure connection is set up
    }

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/inventorydb"; // Update with your DB connection details
    private static final String USER = "user"; // DB username
    private static final String PASSWORD = "User@123"; // DB password

    // Add product with category
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product description: ");
        String description = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter category ID: ");
        int categoryId = scanner.nextInt();

        String query = "INSERT INTO products (name, description, quantity, price, category_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.setInt(5, categoryId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Product added successfully!");
            } else {
                System.out.println("⚠️ Error adding product.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error adding product: " + e.getMessage());
        }
    }

    // View all products with category name
    public void viewProducts() {
        String query = "SELECT * FROM products p JOIN categories c ON p.category_id = c.id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
    
            System.out.println("\nProducts:");
            System.out.printf("%-5s %-20s %-20s %-10s %-10s %-20s\n", "ID", "Name", "Category", "Quantity", "Price", "Description");
            System.out.println("------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-20s %-10d %-10.2f %-20s\n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("description")
                );
            }
            System.out.println("\n");
        } catch (SQLException e) {
            System.out.println("❌ Error viewing products: " + e.getMessage());
        }
    }


    // Update a product with new category, quantity, and price
    public void updateProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the product ID you want to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over

        // Ask for new product details
        System.out.print("Enter new product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new category: ");
        String category = scanner.nextLine();

        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline left-over

        System.out.print("Enter new description: ");
        String description = scanner.nextLine();

        // Update product in database
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE products SET name = ?, category_id = ?, price = ?, description = ? WHERE id = ?")) {

            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setDouble(3, price);
            stmt.setString(4, description);
            stmt.setInt(5, productId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Product updated successfully.");
            } else {
                System.out.println("❌ Product with ID " + productId + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error updating product: " + e.getMessage());
        }
    }

    // Delete a product
    public void deleteProduct(Scanner scanner) {
        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String query = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Product deleted successfully!");
            } else {
                System.out.println("❌ Product not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting product: " + e.getMessage());
        }
    }
}
