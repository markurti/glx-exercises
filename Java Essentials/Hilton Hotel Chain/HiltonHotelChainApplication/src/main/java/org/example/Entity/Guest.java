package org.example.Entity;

public record Guest(
        int guestId,
        String name,
        String email,
        String phone,
        int hotelId
) {
    // Overloaded constructor without guestId (for inserts where DB generates the ID)
    public Guest(String name, String email, String phone, int hotelId) {
        this(0, name, email, phone, hotelId); // guestId = 0 by default
    }

    // No-args constructor
    public Guest() {
        this(0, null, null, null, 0);
    }
}
