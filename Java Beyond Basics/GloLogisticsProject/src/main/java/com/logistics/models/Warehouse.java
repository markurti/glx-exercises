package com.logistics.models;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private Long id;
    private Location location;
    private int capacity;
    private List<Item> inventory;

    public Warehouse(Location location, int capacity) {
        this.location = location;
        this.capacity = capacity;
        this.inventory = new ArrayList<>();
    }

    public void addInventory(Item item) {
        inventory.add(item);
    }

    public void removeInventory(Item item) {
        inventory.remove(item);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Item> getInventory() { return inventory; }
    public void setInventory(List<Item> inventory) { this.inventory = inventory; }

    @Override
    public String toString() {
        return "Warehouse at " + location + " (Cap: " + capacity + ")";
    }
}
