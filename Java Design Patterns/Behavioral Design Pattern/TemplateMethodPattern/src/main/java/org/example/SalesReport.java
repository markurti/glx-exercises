package org.example;

public class SalesReport extends ReportTemplate {
    @Override
    protected void retrieveData() {
        System.out.println("Retrieving sales data...");
        System.out.println("   - Connecting to CRM system");
        System.out.println("   - Fetching sales transactions");
        System.out.println("   - Loading customer information");
        System.out.println("   - Gathering product performance data");
    }

    @Override
    protected void processData() {
        System.out.println("Processing sales data...");
        System.out.println("   - Calculating sales totals by period");
        System.out.println("   - Analyzing customer segments");
        System.out.println("   - Computing conversion rates");
        System.out.println("   - Identifying top performing products");
    }

    @Override
    protected void formatReport() {
        System.out.println("Formatting sales report...");
        System.out.println("   - Creating sales performance dashboards");
        System.out.println("   - Adding trend analysis charts");
        System.out.println("   - Including customer breakdown tables");
        System.out.println("   - Applying sales report template");
    }
}
