package org.example;

import java.util.*;

public class ProxyFileDownloader implements FileDownloader {
    private RealFileDownloader realDownloader;
    private Set<String> cachedFiles;
    private List<String> downloadLog;

    public ProxyFileDownloader() {
        this.cachedFiles = new HashSet<String>();
        this.downloadLog = new ArrayList<String>();
    }

    @Override
    public void downloadFile(String url) {
        logDownload(url);

        if (isFileCached(url)) {
            System.out.println("File found in cache: " + url);
            System.out.println("Serving file from cache (instant access)");
            return;
        }

        // File not in cache use real downloader
        if (realDownloader == null) {
            realDownloader = new RealFileDownloader();
        }

        System.out.println("File not in cache. Downloading...");
        realDownloader.downloadFile(url);
        cacheFile(url);
    }

    private void cacheFile(String url) {
        cachedFiles.add(url);
        System.out.println("File cached: " + url);
    }

    private void logDownload(String url) {
        String timestamp = new Date().toString();
        String logEntry = timestamp + " - Download request: " + url;
        downloadLog.add(logEntry);
        System.out.println("LOG: " + logEntry);
    }

    private boolean isFileCached(String url) {
        return cachedFiles.contains(url);
    }

    // Additional proxy methods for management
    public void showCache() {
        System.out.println("\nCached files:");
        if (cachedFiles.isEmpty()) {
            System.out.println("Cache is empty");
        } else {
            cachedFiles.forEach(url -> System.out.println("- " + url));
        }
    }

    public void showDownloadLog() {
        System.out.println("\nDownload log:");
        if (downloadLog.isEmpty()) {
            System.out.println("No downloads logged");
        } else {
            downloadLog.forEach(System.out::println);
        }
    }

    public void clearCache() {
        cachedFiles.clear();
        System.out.println("Cache cleared");
    }
}
