package org.example;

public class SaleSummary {
    private String productName;
    private double totalSalesAmount;

    public SaleSummary(String productName, double totalSalesAmount) {
        this.productName = productName;
        this.totalSalesAmount = totalSalesAmount;
    }

    public String getProductName() { return productName; }
    public double getTotalSalesAmount() { return totalSalesAmount; }

    @Override
    public String toString() {
        return String.format("SaleSummary{product='%s', totalAmount=$%.2f}",
                productName, totalSalesAmount);
    }
}
