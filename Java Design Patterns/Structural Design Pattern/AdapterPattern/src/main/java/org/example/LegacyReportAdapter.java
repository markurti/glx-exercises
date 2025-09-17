package org.example;

public class LegacyReportAdapter implements NewReportSystem {
    private LegacyReportGenerator legacyGenerator;

    public LegacyReportAdapter(LegacyReportGenerator legacyGenerator) {
        this.legacyGenerator = legacyGenerator;
    }

    @Override
    public String generateReport(String reportData) {
        // Get report from legacy system
        String legacyReport = legacyGenerator.createOldFormatReport(reportData);

        // Convert legacy format to new format and return
        return convertLegacyToNewFormat(legacyReport);
    }

    @Override
    public void saveReport(String report, String filename) {
        // Convert new format back to legacy format for saving
        String legacyFormat = convertNewToLegacyFormat(report);
        legacyGenerator.writeToFile(legacyFormat, filename);
    }

    // Helper methods for format conversion
    private String convertLegacyToNewFormat(String legacyReport) {
        // Remove legacy format markers and convert to JSON-like format
        String content = legacyReport.replace("LEGACY_FORMAT|", "")
                .replace("|END_LEGACY", "");
        return "{\"report\": \"" + content + "\", \"format\": \"new\"}";
    }

    private String convertNewToLegacyFormat(String newReport) {
        // Extract content from new format and convert to legacy format
        String content = newReport.replace("{\"report\": \"", "")
                .replace("\", \"format\": \"new\"}", "");
        return "LEGACY_FORMAT|" + content + "|END_LEGACY";
    }
}
