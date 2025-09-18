package org.example;

public class InventoryReport extends ReportTemplate {
    @Override
    protected void retrieveData() {
        System.out.println("Retrieving inventory data...");
        System.out.println("   - Connecting to warehouse management system");
        System.out.println("   - Fetching current stock levels");
        System.out.println("   - Loading supplier information");
        System.out.println("   - Gathering product movement history");
    }

    @Override
    protected void processData() {
        System.out.println("Processing inventory data...");
        System.out.println("   - Calculating stock turnover rates");
        System.out.println("   - Identifying low stock items");
        System.out.println("   - Computing reorder points");
        System.out.println("   - Analyzing inventory valuation");
    }

    @Override
    protected void formatReport() {
        System.out.println("Formatting inventory report...");
        System.out.println("   - Creating stock level summaries");
        System.out.println("   - Adding reorder recommendations");
        System.out.println("   - Including inventory aging analysis");
        System.out.println("   - Applying inventory management template");
    }
}
