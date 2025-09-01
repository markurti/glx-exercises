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
            System.out.println();

            // Add some stock to product 1
            System.out.println("Old stock in + " + prod1.getName() + ": " + program.getProductStock(1));
            program.addStock(1, 100);
            System.out.println("New stock in " + prod1.getName() + ": " + program.getProductStock(1) + '\n');

            // Reduce some stock from product 2
            System.out.println("Old stock in + " + prod2.getName() + ": " + program.getProductStock(2));
            program.reduceStock(2, 100);
            System.out.println("New stock in " + prod2.getName() + ": " + program.getProductStock(2) + '\n');

            // Reduce some stock from product 3
            System.out.println("Old stock in + " + prod3.getName() + ": " + program.getProductStock(3));
            program.reduceStock(3, 100);
            System.out.println("New stock in " + prod3.getName() + ": " + program.getProductStock(3));

            // Clear database table to re-run the example
            program.clearProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}