package com.inventory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainMenu {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final Connection conn; // Add the conn variable here


    // Constructor accepting Connection
    public MainMenu(Connection conn) {
        this.conn = conn; // Initialize the conn variable
        this.productService = new ProductService();
        this.categoryService = new CategoryService(conn);
    }

    // The showMenu method
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n📦 INVENTORY MENU");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. View Categories");
            System.out.println("6. Add Category");
            System.out.println("7. Run DDL Command");
            System.out.println("8. Commit Changes");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("\033[H\033[2J");
            System.out.flush();

            switch (choice) {
                case 1:
                productService.viewProducts();
                productService.addProduct();
                break;
                
                case 2:
                productService.viewProducts();
                break; 

                case 3:
                productService.viewProducts();
                productService.updateProduct();
                break;

                case 4:
                productService.viewProducts();
                productService.deleteProduct(scanner); // Now works with Scanner
                break;

                case 5:
                categoryService.viewCategories();
                break;

                case 6:
                categoryService.viewCategories();
                categoryService.addCategory(scanner);
                break; 

                case 7: 
                productService.viewProducts();
                runDDLCommand(scanner);
                break;

                case 8: commitChanges();
                break;

                case 9: {
                    System.out.println("👋 Exiting...");
                    return;
                }

                default: System.out.println("❌ Invalid choice, please try again.");
            }
        }
    }

    private void showCategoryMenu() {
        while (true) {
            System.out.println("\n📂 CATEGORIES MENU");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            // System.out.println("3. Update Category");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline


            System.out.print("\033[H\033[2J");
            System.out.flush();
            categoryService.viewCategories();

            switch (choice) {
                case 1 -> categoryService.addCategory(null);
                case 2 -> categoryService.viewCategories();
                // case 3 -> categoryService.updateCategory();
                case 3 -> {
                    return; // Go back to the main menu
                }
                default -> System.out.println("❌ Invalid choice, please try again.");
            }
        }
    }
    
    // Run DDL command
    public void runDDLCommand(Scanner scanner) {
        System.out.print("Enter DDL SQL command: ");
        String ddlCommand = scanner.nextLine();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(ddlCommand);
            System.out.println("✅ DDL command executed successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error executing DDL: " + e.getMessage());
        }
    }
    
    // Commit changes
    public void commitChanges() {
        try {
            conn.commit();
            System.out.println("✅ Changes committed!");
        } catch (SQLException e) {
            System.out.println("❌ Error committing changes: " + e.getMessage());
        }
    }
}