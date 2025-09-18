package org.example;

public abstract class ReportTemplate {
    public final void generateReport() {
        System.out.println("Starting report generation...");
        System.out.println("=".repeat(50));

        retrieveData();
        processData();
        formatReport();

        System.out.println("=".repeat(50));
        System.out.println("Report generation completed successfully!");
        System.out.println();
    }

    // Abstract methods to be implemented by subclasses
    protected abstract void retrieveData();
    protected abstract void processData();
    protected abstract void formatReport();
}
