package org.example.Entity;

import org.example.Database.DatabaseConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        this.status = status;
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
        if (!reservation.getStatus().equals("free") && !reservation.getStatus().equals("cancelled")) {
            System.out.println("You can't make this reservation. Reservation Status: " + reservation.getStatus());
            return;
        }

        if (!isValidDateSequence(reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            System.out.println("Invalid date sequence: Check-out date must come after Check-in date.");
            return;
        }

        if (!isRoomInHotel(reservation.getRoom_id(), reservation.getHotel_id())) {
            System.out.println("Invalid room selection: Room " + reservation.getRoom_id() + " does not belong to Hotel " + reservation.getHotel_id());
            return;
        }

        if (!isRoomAvailable(reservation.getRoom_id())) {
            System.out.println("Room " + reservation.getRoom_id() + " is not available for the selected dates.");
            return;
        }

        String reservationQuery = "INSERT INTO Reservation (guest_id, hotel_id, room_id, checkInDate, checkOutDate, reservationDate, status) VALUES (?, ?, ?, ?, ?, ?, ?::reservation_status_enum)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(reservationQuery)) {

            // Set parameters
            preparedStatement.setInt(1, reservation.getGuest_id());
            preparedStatement.setInt(2, reservation.getHotel_id());
            preparedStatement.setInt(3, reservation.getRoom_id());
            preparedStatement.setDate(4, reservation.getCheckInDate());
            preparedStatement.setDate(5, reservation.getCheckOutDate());
            preparedStatement.setDate(6, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(7, "confirmed"); // Reservation confirmed

            // Execute insert query
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 0) {
                changeRoomStatus(reservation.getRoom_id()); // Change room status
                System.out.println("Reservation has been made. Reservation Status: confirmed");
            } else {
                throw new SQLException("Query execution failed with no rows affected. Reservation Status: " + reservation.getStatus());
            }

        } catch (SQLException e) {
            System.out.println("Failed to make reservation: " + e.getMessage());
        }
    }

    public boolean cancelRoomReservation(int reservation_id) throws SQLException {
        String cancelRoomReservationQuery = "UPDATE Reservation SET status = 'cancelled' WHERE reservation_id = ?";
        String getReservationRoomNumberQuery = "SELECT room_id FROM Reservation WHERE reservation_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(cancelRoomReservationQuery)) {
            // Set parameter
            preparedStatement.setInt(1, reservation_id);

            // Execute update query
            int affectedRows = preparedStatement.executeUpdate();

            // Change room availability to false
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(getReservationRoomNumberQuery)) {
                // Set parameter
                preparedStatement2.setInt(1, reservation_id);

                // Execute select query
                try (ResultSet resultSet = preparedStatement2.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("Failed to fetch room number associated with the reservation.");
                    }

                    int room_id = resultSet.getInt("room_id");
                    changeRoomStatus(room_id); // Change room status

                    return true;
                }
            }
        }
    }

    public List<Reservation> getReservationForHotel(int hotel_id) {
        String fetchReservationsForHotelQuery = "SELECT * FROM Reservation WHERE hotel_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(fetchReservationsForHotelQuery)) {

            // Set parameter
            preparedStatement.setInt(1, hotel_id);

            // Execute select query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Failed to fetch reservations for hotel. Empty ResultSet.");
                }

                List<Reservation> reservations = new ArrayList<>();

                while (resultSet.next()) {
                    int reservation_id = resultSet.getInt("reservation_id");
                    int guest_id = resultSet.getInt("guest_id");
                    int room_id = resultSet.getInt("room_id");
                    Date checkInDate = resultSet.getDate("checkInDate");
                    Date checkOutDate = resultSet.getDate("checkOutDate");
                    String status = resultSet.getString("status");

                    Reservation reservation = new Reservation(reservation_id, guest_id, room_id, checkInDate, checkOutDate,
                            status, hotel_id);

                    reservations.add(reservation);
                }

                return reservations;
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch reservations for hotel with id " + hotel_id + ": " + e.getMessage());
            return null;
        }
    }

    // Helper method to check date sequence
    private boolean isValidDateSequence(Date checkInDate, Date checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            System.out.println("Error: Check-in and check-out dates cannot be null.");
            return false;
        }

        // Convert to LocalDate for easier comparison
        LocalDate checkIn = checkInDate.toLocalDate();
        LocalDate checkOut = checkOutDate.toLocalDate();
        LocalDate today = LocalDate.now();

        // Check if check-out is after check-in
        if (!checkOut.isAfter(checkIn)) {
            return false;
        }

        // Additional validation: Check if check-in is not in the past
        if (checkIn.isBefore(today)) {
            System.out.println("Warning: Check-in date is in the past: " + checkIn);
            return false;
        }

        return true;
    }

    // Helper method to check if selected room is assigned to the selected hotel
    private boolean isRoomInHotel(int roomId, int hotelId) {
        String query = "SELECT hotel_id FROM Room WHERE room_number = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roomHotelId = resultSet.getInt("hotel_id");
                return roomHotelId == hotelId;
            } else {
                System.out.println("Room with ID " + roomId + " does not exist.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error checking room-hotel relationship: " + e.getMessage());
            return false;
        }
    }

    // Helper method to check if the selected room is available
    private boolean isRoomAvailable(int roomId) {
        String query = "SELECT available FROM Room WHERE room_number = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean("available");
            } else {
                System.out.println("Room with ID " + roomId + " does not exist.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error checking room availability: " + e.getMessage());
            return false;
        }
    }

    // Helper method to change room status
    private void changeRoomStatus(int room_id) throws SQLException {
        String updateRoomStatusQuery = "UPDATE Room SET available = FALSE WHERE room_number = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateRoomStatusQuery)) {
            // Set parameter
            preparedStatement.setInt(1, room_id);

            // Execute update query
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
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
