package org.example;

public class Client {
    public static void main(String[] args) {
        System.out.println("=== Report Generation Template Method Pattern Demo ===\n");

        // Generate different types of reports
        System.out.println("FINANCIAL REPORT");
        ReportTemplate financialReport = new FinancialReport();
        financialReport.generateReport();

        System.out.println("SALES REPORT");
        ReportTemplate salesReport = new SalesReport();
        salesReport.generateReport();

        System.out.println("INVENTORY REPORT");
        ReportTemplate inventoryReport = new InventoryReport();
        inventoryReport.generateReport();

        System.out.println("All reports have been generated successfully!");
    }
}