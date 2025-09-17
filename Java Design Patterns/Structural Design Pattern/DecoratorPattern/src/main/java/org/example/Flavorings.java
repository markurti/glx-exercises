package org.example;

public class Flavorings extends CoffeeDecorator {
    private String flavor;

    public Flavorings(Coffee coffee, String flavor) {
        super(coffee);
        this.flavor = flavor;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", " + flavor + " Flavoring";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.75;
    }
}
