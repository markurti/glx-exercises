package com.hitachi.mobile;

import com.hitachi.mobile.entity.Customer;
import com.hitachi.mobile.entity.SimDetails;
import com.hitachi.mobile.exception.CustomerDoesNotExistException;
import com.hitachi.mobile.exception.CustomerTableEmptyException;
import com.hitachi.mobile.exception.SIMDoesNotExistException;
import com.hitachi.mobile.service.CustomerService;
import com.hitachi.mobile.service.SimDetailsService;
import com.hitachi.mobile.util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class HitachiMobileApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerService customerService = new CustomerService();
    private static final SimDetailsService simDetailsService = new SimDetailsService();

    public static void main(String[] args) {
        System.out.println("=== Welcome to Hitachi Mobile SIM Activation Portal ===\n");

        while (true) {
            displayMenu();
            int choice = getChoice();

            try {
                switch (choice) {
                    case 1 -> listCustomersInBangalore();
                    case 2 -> listActiveSimDetails();
                    case 3 -> fetchSimStatus();
                    case 4 -> updateSimStatus();
                    case 5 -> updateCustomerAddress();
                    case 6 -> listAllCustomers();
                    case 7 -> {
                        System.out.println("Thank you for using Hitachi Mobile Portal!");
                        HibernateUtil.shutdown();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. List Customers in Bangalore");
        System.out.println("2. List Active SIM Details");
        System.out.println("3. Fetch SIM Status");
        System.out.println("4. Update SIM Status to Active");
        System.out.println("5. Update Customer Address");
        System.out.println("6. List All Customers");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void listCustomersInBangalore() {
        System.out.println("\n=== Customers in Bangalore ===");
        List<Customer> customers = customerService.getCustomersInBangalore();

        if (customers.isEmpty()) {
            System.out.println("No customers found in Bangalore.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void listActiveSimDetails() {
        System.out.println("\n=== Active SIM Details ===");
        List<SimDetails> activeSimDetails = simDetailsService.getActiveSimDetails();

        if (activeSimDetails.isEmpty()) {
            System.out.println("No active SIM details found.");
        } else {
            activeSimDetails.forEach(System.out::println);
        }
    }

    private static void fetchSimStatus() throws SIMDoesNotExistException {
        System.out.println("\n=== Fetch SIM Status ===");
        System.out.print("Enter SIM Number: ");
        Long simNumber = Long.parseLong(scanner.nextLine());

        System.out.print("Enter Service Number: ");
        Long serviceNumber = Long.parseLong(scanner.nextLine());

        String status = simDetailsService.getSimStatus(simNumber, serviceNumber);
        System.out.println("SIM Status: " + status);
    }

    private static void updateSimStatus() throws SIMDoesNotExistException {
        System.out.println("\n=== Update SIM Status ===");
        System.out.print("Enter SIM ID: ");
        Integer simId = Integer.parseInt(scanner.nextLine());

        simDetailsService.activateSim(simId);
        System.out.println("SIM status updated to active successfully!");
    }

    private static void updateCustomerAddress() throws CustomerDoesNotExistException {
        System.out.println("\n=== Update Customer Address ===");
        System.out.print("Enter Customer Unique ID: ");
        Long uniqueId = Long.parseLong(scanner.nextLine());

        System.out.print("Enter New City: ");
        String city = scanner.nextLine();

        System.out.print("Enter New State: ");
        String state = scanner.nextLine();

        customerService.updateCustomerAddress(uniqueId, city, state);
        System.out.println("Customer address updated successfully!");
    }

    private static void listAllCustomers() throws CustomerTableEmptyException {
        System.out.println("\n=== All Customers ===");
        List<Customer> customers = customerService.getAllCustomers();
        customers.forEach(System.out::println);
    }
}