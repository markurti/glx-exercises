package org.example.Entity;

public class Room {
    private int room_number;
    private String type;
    private boolean available;
    private int hotel_id;

    // Constructor with parameters
    public Room(int room_number, String type, boolean available, int hotel_id) {
        this.room_number = room_number;
        this.type = type;
        this.available = available;
        this.hotel_id = hotel_id;
    }

    // Getters and Setters
    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

}
