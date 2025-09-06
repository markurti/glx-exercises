package org.example.Interface;

import org.example.Entity.Guest;
import org.example.Entity.Hotel;
import org.example.Entity.Room;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ApplicationInterface {
    public void start() {
        while (true) {
            printMenu();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        addHotelInterface();
                        break;
                    case 2:
                        getHotelInterface();
                        break;
                    case 3:
                        addRoomInterface();
                        break;
                    case 4:
                        getRoomInterface();
                        break;
                    case 5:
                        getAllRoomsInterface();
                        break;
                    case 6:
                        addGuestInterface();
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 0:
                        System.out.println("Closing application...");
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Add Hotel");
        System.out.println("2. View Hotel");
        System.out.println("3. Add Room");
        System.out.println("4. View Room");
        System.out.println("5. Get all rooms in Hotel");
        System.out.println("6. Add Guest");
        System.out.println("7. Make Reservation for a room");
        System.out.println("8. Cancel Room Reservation");
        System.out.println("9. Get all reservations in Hotel");
        System.out.println("0. Exit Application");
    }

    private void addHotelInterface() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter hotel name: ");
        String hotelName = scanner.nextLine();
        if (isInvalidString(hotelName)) {
            System.out.println("Invalid hotel name. Try again.");
            return;
        }

        System.out.print("Enter hotel's location: ");
        String hotelLocation = scanner.nextLine();
        if (isInvalidString(hotelName)) {
            System.out.println("Invalid hotel location. Try again.");
            return;
        }

        Hotel hotel = new Hotel(hotelName, hotelLocation, null, null, null);
        hotel.addHotel(hotel);
    }

    private void getHotelInterface() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter hotel id: ");
        int hotelId = scanner.nextInt();

        Hotel hotelInstance = new Hotel();
        Hotel hotel = hotelInstance.getHotel(hotelId);
        if (hotel == null) {
            System.out.println("Hotel not found. Try again.");
            return;
        }

        System.out.println(hotel);
    }

    private void addRoomInterface() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter room type (single or double): ");
        String roomType = scanner.nextLine();
        if (isInvalidString(roomType) && !roomType.equals("single") && !roomType.equals("double")) {
            System.out.println("Invalid room type. Try again.");
            return;
        }

        System.out.print("Enter hotel id: ");
        int hotelId = scanner.nextInt();
        if (!isValidHotelId(hotelId)) {
            System.out.println("Invalid hotel id. Try again.");
            return;
        }

        Room room = new Room(roomType, true, hotelId);
        room.addRoom(room);
    }

    private void getRoomInterface() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();

        Room roomInstance = new Room();
        Room room = roomInstance.getRoom(roomNumber);
        if (room == null) {
            System.out.println("Room not found. Try again.");
            return;
        }

        System.out.println(room);
    }

    private void getAllRoomsInterface() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter hotel id: ");
        int hotelId = scanner.nextInt();
        if (!isValidHotelId(hotelId)) {
            System.out.println("Invalid hotel id. Try again.");
            return;
        }

        Room roomInstance = new Room();
        List<Room> rooms = roomInstance.getRoomsForHotel(hotelId);
        if (rooms != null) {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }

    private void addGuestInterface() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        if (isInvalidString(guestName)) {
            System.out.println("Invalid guest name. Try again.");
            return;
        }

        System.out.print("Enter guest email: ");
        String guestEmail = scanner.nextLine();
        if (isInvalidString(guestEmail) && !guestEmail.contains("@")) {
            System.out.println("Invalid guest email. Try again.");
            return;
        }

        System.out.print("Enter guest phone: ");
        String guestPhone = scanner.nextLine();
        if (!guestPhone.matches("\\d{1,20}") && !guestPhone.trim().isEmpty()) {
            System.out.println("Invalid phone number. Try again.");
            return;
        }

        System.out.print("Enter hotel id: ");
        int hotelId = scanner.nextInt();
        if (!isValidHotelId(hotelId)) {
            System.out.println("Invalid hotel id. Try again.");
            return;
        }

        Guest guest = new Guest(guestName, guestEmail, guestPhone, hotelId);
        guest.addGuest(guest);
    }

    private boolean isValidHotelId(int hotelId) {
        // Check if chosen hotel exists
        Hotel hotelInstance = new Hotel();
        Hotel hotel = hotelInstance.getHotel(hotelId);
        return hotel != null;
    }

    // String validation helper method
    private boolean isInvalidString(String input) {
        // Check if null, empty, or only whitespace
        if (input == null || input.trim().isEmpty()) {
            return true;
        }

        // Check if contains numbers
        if (input.matches(".*\\d.*")) {
            return true;
        }

        // Check if contains only letters, spaces, and basic punctuation
        return !input.trim().matches("^[a-zA-Z\\s.,'@-]+$");
    }
}
