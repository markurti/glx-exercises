package org.example;

public class FinancialReport extends ReportTemplate {
    @Override
    protected void retrieveData() {
        System.out.println("Retrieving financial data...");
        System.out.println("   - Connecting to accounting database");
        System.out.println("   - Fetching revenue, expenses, and profit data");
        System.out.println("   - Loading tax and compliance information");
    }

    @Override
    protected void processData() {
        System.out.println("Processing financial data...");
        System.out.println("   - Calculating profit margins");
        System.out.println("   - Computing tax obligations");
        System.out.println("   - Analyzing financial ratios");
        System.out.println("   - Generating variance analysis");
    }

    @Override
    protected void formatReport() {
        System.out.println("Formatting financial report...");
        System.out.println("   - Creating balance sheets");
        System.out.println("   - Adding profit & loss statements");
        System.out.println("   - Including financial charts and graphs");
        System.out.println("   - Applying corporate financial template");
    }
}
