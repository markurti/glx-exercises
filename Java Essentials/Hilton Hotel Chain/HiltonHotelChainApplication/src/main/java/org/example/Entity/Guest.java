package org.example.Entity;

public class Guest {
    private int guest_id;
    private String email;
    private String name;
    private String phone;
    private int hotel_id;

    public Guest(int guest_id, String email, String name, String phone, int hotel_id) {
        this.guest_id = guest_id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.hotel_id = hotel_id;
    }
}
