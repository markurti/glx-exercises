package org.example;

import org.w3c.dom.ls.LSException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

class SalesAnalyzer {
    // i) Filter out all sales that occurred after a certain date
    public void filterSalesAfterDate(List<Sale> sales, LocalDate date) {
        System.out.println("Filtering sales after " + date);
        System.out.println("=" + "=".repeat(40));

        List<Sale> filteredSales = sales.stream()
                .filter(sale -> sale.getDateOfSale().isAfter(date))
                .toList();

        System.out.println("Sales after " + date + ":");
        filteredSales.forEach(System.out::println);
        System.out.println();
    }

    // ii) Map the sales to their corresponding product names
    public void mapToProductNames(List<Sale> sales) {
        System.out.println("Mapping sales to product names");
        System.out.println("=" + "=".repeat(40));

        List<String> productNames = sales.stream()
                .map(Sale::getProductName)
                .toList();

        System.out.println("All product names from sales:");
        System.out.println(productNames);
        System.out.println();
    }

    // iii) Calculate the total sales amount for a specific product
    public void calculateTotalSalesForProduct(List<Sale> sales, String productName) {
        System.out.println("Calculating total sales for " + productName);
        System.out.println("=" + "=".repeat(40));

        double totalSales = sales.stream()
                .filter(sale -> sale.getProductName().equals(productName))
                .mapToDouble(Sale::getSaleAmount)
                .sum();

        System.out.println("Total sales for " + productName + ": " + totalSales);
        System.out.println();
    }

    // iv) Sort the sales by sale amount in descending order
    public void sortSalesByAmountDescending(List<Sale> sales) {
        System.out.println("Sorting sales by amount descending");
        System.out.println("=" + "=".repeat(40));

        List<Sale> sortedSales = sales.stream()
                .sorted((s1, s2) -> Double.compare(s2.getSaleAmount(), s1.getSaleAmount()))
                .toList();

        System.out.println("Sorted sales:");
        sortedSales.forEach(System.out::println);
        System.out.println();
    }

    // v) Collect the distinct customer IDs from all sales
    public void collectDistinctCustomerIds(List<Sale> sales) {
        System.out.println("Collecting distinct customer ids");
        System.out.println("=" + "=".repeat(40));

        List<String> distinctCustomerIds = sales.stream()
                .map(Sale::getCustomerId)
                .distinct()
                .sorted()
                .toList();

        System.out.println("Distinct customers: " + distinctCustomerIds);
    }

    // vi) Group the sales by product name
    public void groupSalesByProduct(List<Sale> sales) {
        System.out.println("Grouping sales by product");
        System.out.println("=" + "=".repeat(40));

        Map<String, List<Sale>> salesByProduct = sales.stream()
                .collect(Collectors.groupingBy(Sale::getProductName));

        salesByProduct.forEach((product, productSales) -> {
            System.out.println("Product: " + product + " (" + productSales.size() + " sales)");
            productSales.forEach(sale -> System.out.printf("  - $%.2f on %s (Customer: %s)\n", sale.getSaleAmount(), sale.getDateOfSale(), sale.getCustomerId()));
        });
        System.out.println();
    }

    // vii) Filter high-value sales and calculate total
    public void filterHighValueSalesAndCalculateTotal(List<Sale> sales, double threshold) {
        System.out.println("Filtering high value sales and calculate total sales");
        System.out.println("=" + "=".repeat(40));

        List<Sale> highValueSales = sales.stream()
                .filter(sale -> sale.getSaleAmount() > threshold)
                .toList();

        System.out.println("High-value sales (> $" + threshold + "):");
        highValueSales.forEach(System.out::println);
    }

    // viii) Parallel streams example
    public void parallelStreamSortSalesByAmountDescending(List<Sale> sales) {
        System.out.println("Parallel stream sort sales by amount descending");
        System.out.println("=" + "=".repeat(40));

        List<Sale> sortedSales = sales.parallelStream()
                .sorted(Comparator.comparing(Sale::getSaleAmount).reversed())
                .toList();

        System.out.println("Sorted sales:");
        sortedSales.forEach(System.out::println);
        System.out.println();
    }

    // ix) Map to SaleSummary objects
    public void mapToSaleSummary(List<Sale> sales) {
        System.out.println("Mapping to sale summary");
        System.out.println("=" + "=".repeat(40));

        List<SaleSummary> saleSummaries = sales.stream()
                .collect(Collectors.groupingBy(Sale::getProductName,
                        Collectors.summingDouble(Sale::getSaleAmount)))
                .entrySet().stream()
                .map(entry -> new SaleSummary(entry.getKey(), entry.getValue()))
                .sorted((s1, s2) -> Double.compare(s2.getTotalSalesAmount(), s1.getTotalSalesAmount()))
                .toList();

        System.out.println("Sales summary by product:");
        saleSummaries.forEach(System.out::println);
        System.out.println();
    }

    // x) Calculate the average sales amount
    public void calculateAverageSalesAmount(List<Sale> sales) {
        System.out.println("Average sales amount analysis");
        System.out.println("=" + "=".repeat(40));

        OptionalDouble averageAmount = sales.stream()
                .mapToDouble(Sale::getSaleAmount)
                .average();

        double totalSales = sales.stream()
                .mapToDouble(Sale::getSaleAmount)
                .sum();

        System.out.println("Average sales amount: " + averageAmount.orElse(0.0));
        System.out.println("Total sales amount: " + totalSales);
        System.out.println();
    }
}