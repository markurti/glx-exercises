package org.example.Entity;

import org.example.Database.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestDAO {

    public void addGuest(Guest guest) {
        String addGuestQuery = "INSERT INTO Guest (name, email, phone, hotel_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addGuestQuery)) {

            preparedStatement.setString(1, guest.name());
            preparedStatement.setString(2, guest.email());
            preparedStatement.setString(3, guest.phone());
            preparedStatement.setInt(4, guest.hotelId());

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

    public Guest getGuest(int guestId) {
        String getGuestQuery = "SELECT * FROM Guest WHERE guest_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getGuestQuery)) {

            preparedStatement.setInt(1, guestId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException("Guest does not exist.");
            } else {
                return new Guest(
                        resultSet.getInt("guest_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getInt("hotel_id")
                );
            }
        } catch (SQLException e) {
            System.out.println("Could not get guest: " + e.getMessage());
            return null;
        }
    }
}
