package org.example;

public class Client {
    public static void main(String[] args) {
        System.out.println("Adapter Pattern Demo - Legacy Report System");
        System.out.println("==========================================");

        // Create legacy system
        LegacyReportGenerator legacySystem = new LegacyReportGenerator();

        // Create adapter to make legacy system work with new interface
        NewReportSystem adaptedSystem = new LegacyReportAdapter(legacySystem);

        // Use the adapted system with new interface
        String reportData = "Monthly sales data: $50,000 revenue";

        System.out.println("1. Generating report using adapted legacy system:");
        String report = adaptedSystem.generateReport(reportData);
        System.out.println("Generated report: " + report);

        System.out.println("\n2. Saving report using adapted legacy system:");
        adaptedSystem.saveReport(report, "monthly_report.txt");

        System.out.println("\n3. Demonstrating direct legacy system usage:");
        String directLegacyReport = legacySystem.createOldFormatReport(reportData);
        System.out.println("Direct legacy report: " + directLegacyReport);
        legacySystem.writeToFile(directLegacyReport, "legacy_report.txt");

        // Demonstrate that the same interface can work with different implementations
        System.out.println("\n4. Using adapted system seamlessly:");
        processReports(adaptedSystem);
    }

    // Method that works with any NewReportSystem implementation
    private static void processReports(NewReportSystem reportSystem) {
        String report = reportSystem.generateReport("Quarterly data: $150,000 revenue");
        System.out.println("Processed report: " + report);
        reportSystem.saveReport(report, "processed_report.txt");
    }
}