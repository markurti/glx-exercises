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
}
