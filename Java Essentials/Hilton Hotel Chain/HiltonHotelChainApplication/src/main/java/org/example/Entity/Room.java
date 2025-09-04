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
}
