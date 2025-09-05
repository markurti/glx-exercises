package org.example.Entity;

import org.example.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private int hotel_id;
    private String name;
    private String location;
    private List<Room> rooms;
    private List<Guest> guests;
    private List<Reservation> reservations;

    // Constructor with parameters
    public Hotel(String name, String location, List<Room> rooms, List<Guest> guests, List<Reservation> reservations) {
        this.name = name;
        this.location = location;
        this.rooms = rooms != null ? rooms : new ArrayList<>();
        this.guests = guests != null ? guests : new ArrayList<>();
        this.reservations = reservations != null ? reservations : new ArrayList<>();
    }

    // Constructor for basic hotel info
    public Hotel(int hotel_id, String location, String name) {
        this.hotel_id = hotel_id;
        this.location = location;
        this.name = name;
    }

    public void addHotel(Hotel hotel) {
        String addHotelQuery = "INSERT INTO Hotel (name, location) VALUES (?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
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
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add Hotel to the database: " + e.getMessage());
        }
    }

    public Hotel getHotel(int hotel_id) {
        String getHotelQuery = "SELECT * FROM Hotel WHERE hotel_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getHotelQuery)) {

            // Set parameter
            preparedStatement.setInt(1, hotel_id);

            // Execute query and get result
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Hotel with id " + hotel_id + " does not exist.");
                }

                // Get data from result set
                int id = resultSet.getInt("hotel_id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");

                Hotel hotel = new Hotel(id, name, location);

                hotel.loadRooms(connection, id);
                hotel.loadGuests(connection, id);
                hotel.loadReservations(connection, id);

                return hotel;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get Hotel with hotel_id " + hotel_id + ": " + e.getMessage());
        }
    }

    // Helper method to fetch Hotel object from the database
    private void loadRooms(Connection connection, int hotel_id) throws SQLException {
        String roomQuery = "SELECT * FROM Room WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(roomQuery)) {
            preparedStatement.setInt(1, hotel_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Room room = new Room(resultSet.getInt("room_number"), resultSet.getString("type"),
                            resultSet.getBoolean("available"), resultSet.getInt("hotel_id"));

                    rooms.add(room);
                }
            }
        }
    }

    // Helper method to fetch Hotel object from the database
    private void loadGuests(Connection connection, int hotel_id) throws SQLException {
        String guestQuery = "SELECT * FROM Guest WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(guestQuery)) {
            preparedStatement.setInt(1, hotel_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Guest guest = new Guest(resultSet.getInt("guest_id"), resultSet.getString("name"),
                            resultSet.getString("email"), resultSet.getString("phone"), resultSet.getInt("hotel_id"));

                    guests.add(guest);
                }
            }
        }
    }

    // Helper method to fetch Hotel object from the database
    private void loadReservations(Connection connection, int hotel_id) throws SQLException {
        String reservationQuery = "SELECT * FROM Reservation WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(reservationQuery)) {
            preparedStatement.setInt(1, hotel_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Reservation reservation = new Reservation(resultSet.getInt("reservation_id"), resultSet.getInt("guest_id"),
                            resultSet.getInt("room_id"), resultSet.getDate("checkInDate"), resultSet.getDate("checkOutDate"),
                            resultSet.getString("status"), resultSet.getInt("hotel_id"));

                    reservations.add(reservation);
                }
            }
        }
    }

    // Getters and setters
    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotel_id=" + hotel_id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", rooms=" + rooms.size() +
                ", guests=" + guests.size() +
                ", reservations=" + reservations.size() +
                '}';
    }
}
