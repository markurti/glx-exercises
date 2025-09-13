package org.example;

import java.time.LocalDate;

public class Sale {
    private String productName;
    private double saleAmount;
    private String customerId;
    private LocalDate dateOfSale;

    public Sale(String productName, double saleAmount, String customerId, LocalDate dateOfSale) {
        this.productName = productName;
        this.saleAmount = saleAmount;
        this.customerId = customerId;
        this.dateOfSale = dateOfSale;
    }

    // Getters
    public String getProductName() {
        return productName;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    @Override
    public String toString() {
        return String.format("Sale{product='%s', amount=$%.2f, customer='%s', date=%s}", productName, saleAmount, customerId, dateOfSale);
    }
}
