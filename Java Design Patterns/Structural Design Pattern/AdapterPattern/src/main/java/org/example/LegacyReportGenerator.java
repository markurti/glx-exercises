package org.example;

public class LegacyReportGenerator {
    public String createOldFormatReport(String data) {
        return "LEGACY_FORMAT|" + data + "|END_LEGACY";
    }

    public void writeToFile(String report, String filename) {
        System.out.println("Legacy system writing to file: " + filename);
        System.out.println("Content: " + report);
    }
}
