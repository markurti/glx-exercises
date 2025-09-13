package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create sample sales data
        List<Sale> salesData = createSampleData();


    }

    // Create sample sales data for testing
    private static List<Sale> createSampleData() {
        return Arrays.asList(
                new Sale("Smartphone", 899.99, "CUST001", LocalDate.of(2022, 12, 15)),
                new Sale("Laptop", 1299.99, "CUST002", LocalDate.of(2023, 1, 10)),
                new Sale("Smartphone", 849.99, "CUST003", LocalDate.of(2023, 2, 5)),
                new Sale("Tablet", 399.99, "CUST001", LocalDate.of(2023, 1, 20)),
                new Sale("Headphones", 199.99, "CUST004", LocalDate.of(2022, 11, 30)),
                new Sale("Laptop", 1199.99, "CUST005", LocalDate.of(2023, 3, 8)),
                new Sale("Smartphone", 799.99, "CUST002", LocalDate.of(2023, 2, 18)),
                new Sale("Tablet", 299.99, "CUST006", LocalDate.of(2023, 1, 25)),
                new Sale("Headphones", 149.99, "CUST003", LocalDate.of(2023, 2, 12)),
                new Sale("Laptop", 1399.99, "CUST007", LocalDate.of(2023, 3, 15)),
                new Sale("Smartphone", 699.99, "CUST008", LocalDate.of(2022, 12, 20)),
                new Sale("Tablet", 499.99, "CUST004", LocalDate.of(2023, 1, 5)),
                new Sale("Headphones", 89.99, "CUST009", LocalDate.of(2022, 12, 10)),
                new Sale("Laptop", 999.99, "CUST001", LocalDate.of(2023, 2, 28)),
                new Sale("Smartphone", 949.99, "CUST010", LocalDate.of(2023, 3, 20))
        );
    }
}