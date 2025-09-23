package com.logistics.models;

public class Vehicle {
    private Long id;
    private String plateNumber;
    private String type;
    private float capacity;

    public Vehicle(String plateNumber, String type, float capacity) {
        this.plateNumber = plateNumber;
        this.type = type;
        this.capacity = capacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public float getCapacity() { return capacity; }
    public void setCapacity(float capacity) { this.capacity = capacity; }
}
