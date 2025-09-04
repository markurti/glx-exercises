package org.example.Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Hotel {
    private int hotel_id;
    private String name;
    private String location;
    private List<Room> rooms;
    private List<Guest> guests;
    private List<Reservation> reservations;

    public Hotel(String name, String location, List<Room> rooms, List<Guest> guests, List<Reservation> reservations) {
        this.name = name;
        this.location = location;
        this.rooms = rooms;
        this.guests = guests;
        this.reservations = reservations;
    }

    public void addHotel(Hotel hotel) {
        String addHotelQuery = "INSERT INTO Hotel VALUES (?, ?)";

        try {
            String url = "jdbc:postgresql://localhost:5432/HiltonHotelDatabase";
            String username = "postgres";
            String password = "password";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(addHotelQuery)) {

                // Set parameters
                preparedStatement.setString(1, hotel.getName());
                preparedStatement.setString(2, hotel.getLocation());

                // Execute query
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Hotel added successfully.");
                } else {
                    System.out.println("Failed to add Hotel to the database.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add Hotel to the database: " + e.getMessage());
        }
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
