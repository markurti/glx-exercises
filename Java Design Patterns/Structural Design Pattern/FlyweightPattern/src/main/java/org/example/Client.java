package org.example;

public class Client {
    public static void main(String[] args) {
        System.out.println("Flyweight Pattern Demo - Text Character System");
        System.out.println("==============================================");

        // Create a document
        Document document = new Document();

        // Add characters to document - demonstrating flyweight reuse
        System.out.println("\n1. Adding characters to document:");
        document.addCharacter('H', TextStyle.BOLD, new Point(10, 20), "14px", "Black");
        document.addCharacter('e', TextStyle.NORMAL, new Point(20, 20), "14px", "Black");
        document.addCharacter('l', TextStyle.NORMAL, new Point(30, 20), "14px", "Black");
        document.addCharacter('l', TextStyle.NORMAL, new Point(40, 20), "14px", "Black"); // Reuses 'l'
        document.addCharacter('o', TextStyle.ITALIC, new Point(50, 20), "14px", "Red");

        // Add more characters showing reuse
        document.addCharacter('H', TextStyle.BOLD, new Point(10, 40), "16px", "Blue"); // Reuses 'H'
        document.addCharacter('i', TextStyle.UNDERLINE, new Point(20, 40), "16px", "Blue");

        // Render the document
        document.renderDocument();

        // Demonstrate editing
        System.out.println("\n2. Demonstrating character editing:");
        TextCharacter flyweight = TextCharacterFactory.getCharacter('A', TextStyle.BOLD);
        flyweight.edit("Modified content for character A");

        // Create another document to show more flyweight reuse
        System.out.println("\n3. Creating second document with repeated characters:");
        Document document2 = new Document();

        // Add repeated characters
        String text = "HELLO WORLD";
        int x = 10;
        for (char ch : text.toCharArray()) {
            if (ch != ' ') {
                document2.addCharacter(ch, TextStyle.NORMAL, new Point(x, 60), "12px", "Green");
            }
            x += 15;
        }
    }
}