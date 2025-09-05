CREATE TYPE room_type_enum AS ENUM ('single', 'double');
CREATE TYPE reservation_status_enum AS ENUM ('confirmed', 'pending', 'free', 'cancelled');

CREATE TABLE Hotel (
    hotel_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location TEXT NOT NULL
);

CREATE TABLE Guest (
    guest_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    hotel_id INT NOT NULL,
    CONSTRAINT fk_guest_hotel FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id) ON DELETE CASCADE
);

CREATE TABLE Room (
    room_number SERIAL PRIMARY KEY,
    type room_type_enum NOT NULL,
    available BOOLEAN NOT NULL DEFAULT TRUE,
    hotel_id INT NOT NULL,
    CONSTRAINT fk_room_hotel FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id) ON DELETE CASCADE
);

CREATE TABLE Reservation (
    reservation_id SERIAL PRIMARY KEY,
    guest_id INT NOT NULL,
    hotel_id INT NOT NULL,
    room_id INT NOT NULL,
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL,
    reservationDate DATE NOT NULL,
    status reservation_status_enum NOT NULL,
    CONSTRAINT fk_reservation_guest FOREIGN KEY (guest_id) REFERENCES Guest(guest_id) ON DELETE CASCADE,
    CONSTRAINT fk_reservation_hotel FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id) ON DELETE CASCADE
);