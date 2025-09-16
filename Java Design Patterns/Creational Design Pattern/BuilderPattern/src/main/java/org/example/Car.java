package org.example;

public class Car {
    private String brand;
    private String model;
    private String color;
    private String engine;
    private String transmission;
    private String fuelType;

    private Car(CarBuilder builder) {
        this.brand = builder.brand;
        this.model = builder.model;
        this.color = builder.color;
        this.engine = builder.engine;
        this.transmission = builder.transmission;
        this.fuelType = builder.fuelType;
    }

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

    public static class CarBuilder {
        private String brand;
        private String model;
        private String color;
        private String engine;
        private String transmission;
        private String fuelType;

        public CarBuilder withBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public CarBuilder withModel(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder withColor(String color) {
            this.color = color;
            return this;
        }

        public CarBuilder withEngine(String engine) {
            this.engine = engine;
            return this;
        }

        public CarBuilder withTransmission(String transmission) {
            this.transmission = transmission;
            return this;
        }

        public CarBuilder withFuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}
