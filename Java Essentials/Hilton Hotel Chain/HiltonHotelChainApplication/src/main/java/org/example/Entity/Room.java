package org.example.Entity;

import org.example.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    // Constructor for room without room number
    public Room(String type, boolean isAvailable, int hotel_id) {
        this.type = type;
        this.available = isAvailable;
        this.hotel_id = hotel_id;
    }

    public void addRoom(Room room) {
        String addRoomQuery = "INSERT INTO Room VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(addRoomQuery)) {

            // Set parameters
            String roomType = room.getType();
            // Check room type to be 'single' or 'double'
            if (roomType.equals("single") || roomType.equals("double")) {
                preparedStatement.setString(1, room.getType());
            } else {
                throw new SQLException("Room type has to be one of the following: 'single', 'double'. Instead got: " + roomType);
            }
            preparedStatement.setBoolean(2, room.isAvailable());
            preparedStatement.setInt(3, room.getHotel_id());

            // Execute insert query
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 0) {
                System.out.println("New room has been added to the database.");
            } else {
                System.out.println("Failed to add room to the database.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to add room to the database: " + e.getMessage());
        }
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
