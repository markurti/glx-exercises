package org.example.Entity;

import java.sql.Date;

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
