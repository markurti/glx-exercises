package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Product prod1 = new Product(1, "Apple", 100);
        Product prod2 = new Product(2, "Banana", 200);
        Product prod3 = new Product(3, "Orange", 0);

        ProductDao program = new ProductDao();

        try {
            // Add some sample products to the database
            program.insert(prod1);
            program.insert(prod2);
            program.insert(prod3);

            // Add some stock to product 1
            System.out.println("Old stock in + " + prod1.getName() + ": " + program.getProductStock(1));
            program.addStock(1, 100);
            System.out.println("New stock in " + prod1.getName() + ": " + program.getProductStock(1));

            // Clear database table to re-run the example
            //program.clearProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}