package org.example;

import java.util.InputMismatchException;
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
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
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
}
