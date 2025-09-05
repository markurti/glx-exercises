package org.example.Entity;

import org.example.DatabaseConnectionManager;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String addRoomQuery = "INSERT INTO Room (type, available, hotel_id) VALUES (?, ?, ?)";

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

    public List<Room> getRoomsForHotel(int hotel_id) {
        String fetchRoomsQuery = "SELECT * FROM Room WHERE hotel_id = ?";
        List<Room> rooms = new ArrayList<>();

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(fetchRoomsQuery)) {

            // Set parameter
            preparedStatement.setInt(1, hotel_id);

            // Execute select query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No rooms found for hotel with id: " + hotel_id);
                    return rooms; // Return empty list
                }

                while (resultSet.next()) {
                    int room_number = resultSet.getInt("room_number");
                    String type = resultSet.getString("type");
                    boolean available = resultSet.getBoolean("available");

                    Room room = new Room(room_number, type, available, hotel_id);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch rooms from the database: " + e.getMessage());
            return null;
        }
        return rooms;
    }

    public Room getRoom(int room_number) {
        String getRoomQuery = "SELECT * FROM Room WHERE room_number = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getRoomQuery)) {

            // Set parameter
            preparedStatement.setInt(1, room_number);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No room found with room number: " + room_number);
                }

                return new Room(resultSet.getInt("room_number"), resultSet.getString("type"),
                        resultSet.getBoolean("available"), resultSet.getInt("hotel_id"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get room with room number " + room_number + ": " + e.getMessage());
            return null;
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
