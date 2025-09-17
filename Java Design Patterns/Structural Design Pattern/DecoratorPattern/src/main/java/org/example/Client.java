package org.example;

public class Client {
    public static void main(String[] args) {
        System.out.println("Decorator Pattern Demo - Coffee Customization");
        System.out.println("=============================================");

        // Basic coffee
        Coffee coffee1 = new BasicCoffee();
        printCoffeeDetails(coffee1);

        // Coffee with milk
        Coffee coffee2 = new Milk(new BasicCoffee());
        printCoffeeDetails(coffee2);

        // Coffee with milk and sugar
        Coffee coffee3 = new Sugar(new Milk(new BasicCoffee()));
        printCoffeeDetails(coffee3);

        // Fully customized coffee with all additives
        Coffee coffee4 = new Flavorings(
                new Sugar(
                        new Milk(
                                new BasicCoffee())), "Vanilla");
        printCoffeeDetails(coffee4);

        // Another combination - double milk with hazelnut
        Coffee coffee5 = new Flavorings(
                new Milk(
                        new Milk(
                                new BasicCoffee())), "Hazelnut");
        printCoffeeDetails(coffee5);

        // Complex customization
        Coffee coffee6 = new Sugar(
                new Flavorings(
                        new Sugar(
                                new Milk(
                                        new BasicCoffee())), "Caramel"));
        printCoffeeDetails(coffee6);
    }

    private static void printCoffeeDetails(Coffee coffee) {
        System.out.printf("Order: %s%n", coffee.getDescription());
        System.out.printf("Cost: $%.2f%n", coffee.getCost());
        System.out.println("---");
    }
}