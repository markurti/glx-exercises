package org.example;

public class Client {
    public static void main(String[] args) {
        // Create files
        File file1 = new File("document.txt", 1024);
        File file2 = new File("image.jpg", 2048);
        File file3 = new File("video.mp4", 5120);
        File file4 = new File("readme.txt", 512);
        File file5 = new File("config.xml", 256);

        // Create directories
        Directory rootDir = new Directory("root");
        Directory documentsDir = new Directory("documents");
        Directory mediaDir = new Directory("media");
        Directory smallMediaDir = new Directory("small-media");
        Directory configDir = new Directory("config");

        // Build file system structure
        documentsDir.add(file1);
        documentsDir.add(file4);

        smallMediaDir.add(file2);

        mediaDir.add(file2);
        mediaDir.add(file3);
        mediaDir.add(smallMediaDir);

        configDir.add(file5);

        rootDir.add(documentsDir);
        rootDir.add(mediaDir);
        rootDir.add(configDir);

        // Demonstrate operations
        System.out.println("File System Structure:");
        System.out.println("=====================");
        rootDir.printDetails("");

        System.out.println("\nTotal size of root directory: " + rootDir.getSize() + " mb");
        System.out.println("Total size of documents directory: " + documentsDir.getSize() + " mb");
        System.out.println("Total size of media directory: " + mediaDir.getSize() + " mb");
    }
}