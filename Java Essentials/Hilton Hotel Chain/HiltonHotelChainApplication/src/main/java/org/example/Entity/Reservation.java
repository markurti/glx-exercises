package org.example.Entity;

import org.example.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class Reservation {
    private int reservation_id;
    private int guest_id;
    private int room_id;
    private Date checkInDate;
    private Date checkOutDate;
    private String status;
    private int hotel_id;

    // Constructor with parameters
    public Reservation(int reservation_id, int guest_id, int room_id, Date checkInDate, Date checkOutDate, String status, int hotel_id) {
        this.reservation_id = reservation_id;
        this.guest_id = guest_id;
        this.room_id = room_id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        if (status.equals("free")) {
            this.status = status;
        } else {
            this.status = "free";
        }
        this.hotel_id = hotel_id;
    }

    // Constructor without index and status setter
    public Reservation(int guest_id, int room_id, Date checkInDate, Date checkOutDate, int hotel_id) {
        this.guest_id = guest_id;
        this.room_id = room_id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = "free";
        this.hotel_id = hotel_id;
    }

    public void makeReservation(Reservation reservation) {
        if (!reservation.getStatus().equals("free")) {
            System.out.println("You can't make this reservation. Reservation Status: " + reservation.getStatus());
            return;
        }

        String reservationQuery = "INSERT INTO Reservation VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(reservationQuery)) {

            // Set parameters
            preparedStatement.setInt(1, reservation.getGuest_id());
            preparedStatement.setInt(2, reservation.getRoom_id());
            preparedStatement.setDate(3, reservation.getCheckInDate());
            preparedStatement.setDate(4, reservation.getCheckOutDate());
            preparedStatement.setString(5, "confirmed"); // Reservation confirmed
            preparedStatement.setInt(6, reservation.getHotel_id());

            // Execute insert query
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 0 && changeRoomStatus(reservation.getRoom_id())) {
                System.out.println("Reservation has been made. Reservation Status: confirmed");
            } else {
                throw new SQLException("Query execution failed with no rows affected. Reservation Status: " + reservation.getStatus());
            }

        } catch (SQLException e) {
            System.out.println("Failed to make reservation: " + e.getMessage());
        }
    }

    // Helper method to change room status
    private boolean changeRoomStatus(int room_id) throws SQLException {
        String updateRoomStatusQuery = "UPDATE Room SET available = FALSE WHERE room_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateRoomStatusQuery)) {
            // Set parameter
            preparedStatement.setInt(1, room_id);

            // Execute update query
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 0) {
                return true;
            } else {
                throw new SQLException("Failed to change reserved room availability to FALSE.");
            }
        }
    }


    // Getters and setters

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }
}
