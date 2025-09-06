package org.example.Entity;

import org.example.Database.DatabaseConnectionManager;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Guest {
    private int guest_id;
    private String email;
    private String name;
    private String phone;
    private int hotel_id;

    // Constructor with parameters
    public Guest(int guest_id, String email, String name, String phone, int hotel_id) {
        this.guest_id = guest_id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.hotel_id = hotel_id;
    }

    // Constructor without id
    public Guest(String name, String email, String phone, int hotel_id) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.hotel_id = hotel_id;
    }

    // Parameterless constructor
    public Guest() {}

    public void addGuest(Guest guest) {
        String addGuestQuery = "INSERT INTO Guest (name, email, phone, hotel_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(addGuestQuery)) {

            // Set parameters
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setString(2, guest.getEmail());
            preparedStatement.setString(3, guest.getPhone());
            preparedStatement.setInt(4, guest.getHotel_id());

            // Execute insert query
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 0) {
                System.out.println("New guest added to the database.");
            } else {
                throw new SQLException("Query execution resulted with: " + affectedRows + " affected rows.");
            }
        } catch (SQLException e) {
            System.out.println("Could not add new guest: " + e.getMessage());
        }
    }

    public Guest getGuest(int guest_id) {
        String getGuestQuery = "SELECT * FROM Guest WHERE guest_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getGuestQuery)) {
            // Set parameter
            preparedStatement.setInt(1, guest_id);

            // Execute select query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException("Guest does not exist.");
            }
            else {
                return new Guest(resultSet.getInt("guest_id"), resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("phone"), resultSet.getInt("hotel_id"));
            }
        } catch (SQLException e) {
            System.out.println("Could not get guest: " + e.getMessage());
            return null;
        }
    }

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }
}
