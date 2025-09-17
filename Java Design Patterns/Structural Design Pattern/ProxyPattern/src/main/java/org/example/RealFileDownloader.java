package org.example;

public class RealFileDownloader implements FileDownloader {
    @Override
    public void downloadFile(String url) {
        System.out.println("Connecting to server...");
        simulateNetworkDelay();
        System.out.println("Downloading file from: " + url);
        simulateDownload();
        System.out.println("File downloaded successfully from: " + url);
    }

    private void simulateNetworkDelay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void simulateDownload() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
