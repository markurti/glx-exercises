package org.example;

public class Client {
    public static void main(String[] args) {
        System.out.println("Proxy Pattern Demo - File Downloader System");
        System.out.println("==========================================");

        // Create downloaders
        FileDownloader realDownloader = new RealFileDownloader();
        ProxyFileDownloader proxyDownloader = new ProxyFileDownloader();

        // Test URLs
        String url1 = "https://example.com/document.pdf";
        String url2 = "https://example.com/image.jpg";
        String url3 = "https://example.com/video.mp4";

        // Demonstrate real downloader
        System.out.println("\n1. Using Real File Downloader:");
        System.out.println("------------------------------");
        realDownloader.downloadFile(url1);

        // Demonstrate proxy downloader with caching
        System.out.println("\n2. Using Proxy File Downloader (first time):");
        System.out.println("--------------------------------------------");
        proxyDownloader.downloadFile(url1);

        System.out.println("\n3. Using Proxy File Downloader (cached):");
        System.out.println("----------------------------------------");
        proxyDownloader.downloadFile(url1); // Should serve from cache

        // Download more files
        System.out.println("\n4. Downloading additional files:");
        System.out.println("--------------------------------");
        proxyDownloader.downloadFile(url2);
        proxyDownloader.downloadFile(url3);

        // Show cache contents
        System.out.println("\n5. Cache contents:");
        System.out.println("-----------------");
        proxyDownloader.showCache();

        // Test cached access
        System.out.println("\n6. Accessing cached files:");
        System.out.println("-------------------------");
        proxyDownloader.downloadFile(url2); // Should serve from cache
        proxyDownloader.downloadFile(url3); // Should serve from cache

        // Show download log
        System.out.println("\n7. Download log:");
        System.out.println("---------------");
        proxyDownloader.showDownloadLog();

        // Clear cache and test
        System.out.println("\n8. Testing after cache clear:");
        System.out.println("-----------------------------");
        proxyDownloader.clearCache();
        proxyDownloader.downloadFile(url1); // Should download again
    }
}