package org.example;

public class Car {
    private String brand;
    private String model;
    private String color;
    private String engine;
    private String transmission;
    private String fuelType;

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getEngine() {
        return engine;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getModel() {
        return model;
    }

    public String getTransmission() {
        return transmission;
    }

    @Override
    public String toString() {
        return "Car [brand=" + brand + ", model=" + model + ", color=" + color + ", engine=" + engine
                + ", transmission=" + transmission + ", fuelType=" + fuelType + "]";
    }
}
