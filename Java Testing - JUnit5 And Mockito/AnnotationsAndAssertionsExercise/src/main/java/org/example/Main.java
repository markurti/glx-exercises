package org.example;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CustomerServiceImpl customerService;
    private static Scanner scanner;
    private static boolean useDatabase = true;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        try {
            initializeSystem();
            showWelcomeMessage();
            runMainMenu();
        } catch (Exception e) {
            System.out.println("Error initializing system: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void initializeSystem() throws SQLException {
        if (useDatabase) {
            DatabaseConnection dbConnection = new DatabaseConnection();
            CustomerRepositoryImpl repository = new CustomerRepositoryImpl(dbConnection);
            customerService = new CustomerServiceImpl(repository);
            System.out.println("System initialized with PostgreSQL database.");
        } else {
            InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
            customerService = new CustomerServiceImpl(repository);
            System.out.println("System initialized with in-memory storage.");
        }
    }

    private static void showWelcomeMessage() {
        System.out.println("\n========================================");
        System.out.println("  Customer Management System");
        System.out.println("========================================");
    }

    private static void runMainMenu() {
        boolean running = true;

        while (running) {
            displayMainMenu();

            try {
                int choice = getIntInput("Select an option (1-6): ");
                System.out.println();

                switch (choice) {
                    case 1:
                        createCustomer();
                        break;
                    case 2:
                        readCustomerById();
                        break;
                    case 3:
                        readAllCustomers();
                        break;
                    case 4:
                        updateCustomer();
                        break;
                    case 5:
                        deleteCustomer();
                        break;
                    case 6:
                        running = false;
                        System.out.println("Thank you for using Customer Management System!");
                        break;
                    default:
                        System.out.println("Invalid option! Please select a number between 1 and 6.");
                }

                if (running && choice >= 1 && choice <= 5) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Create Customer");
        System.out.println("2. Read Customer by ID");
        System.out.println("3. Read All Customers");
        System.out.println("4. Update Customer");
        System.out.println("5. Delete Customer");
        System.out.println("6. Exit");
        System.out.println("-----------------");
    }

    // CREATE CUSTOMER
    private static void createCustomer() {
        System.out.println("CREATE NEW CUSTOMER");
        System.out.println("-------------------");

        try {
            int custId = getIntInput("Customer ID: ");

            System.out.print("Customer Name: ");
            String customerName = scanner.nextLine().trim();

            System.out.print("Contact Number: ");
            String contactNumber = scanner.nextLine().trim();

            System.out.print("Address: ");
            String address = scanner.nextLine().trim();

            Customer newCustomer = new Customer(custId, customerName, contactNumber, address);
            customerService.addCustomer(newCustomer);

            System.out.println("\nCustomer created successfully!");
            displayCustomer(newCustomer);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }

    // READ CUSTOMER BY ID
    private static void readCustomerById() {
        System.out.println("FIND CUSTOMER BY ID");
        System.out.println("-------------------");

        try {
            int custId = getIntInput("Enter Customer ID: ");

            Customer customer = customerService.getCustomerById(custId);

            if (customer != null) {
                System.out.println("\nCustomer found:");
                displayCustomer(customer);
            } else {
                System.out.println("Customer with ID " + custId + " not found.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error reading customer: " + e.getMessage());
        }
    }

    // READ ALL CUSTOMERS
    private static void readAllCustomers() {
        System.out.println("ALL CUSTOMERS");
        System.out.println("-------------");

        try {
            List<Customer> customers = customerService.getAllCustomers();

            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                System.out.println("Found " + customers.size() + " customer(s):\n");

                for (int i = 0; i < customers.size(); i++) {
                    System.out.println("Customer " + (i + 1) + ":");
                    displayCustomer(customers.get(i));
                    if (i < customers.size() - 1) {
                        System.out.println();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading customers: " + e.getMessage());
        }
    }

    // UPDATE CUSTOMER
    private static void updateCustomer() {
        System.out.println("UPDATE CUSTOMER");
        System.out.println("---------------");

        try {
            int custId = getIntInput("Enter Customer ID to update: ");

            Customer existingCustomer = customerService.getCustomerById(custId);
            if (existingCustomer == null) {
                System.out.println("Customer with ID " + custId + " not found.");
                return;
            }

            System.out.println("\nCurrent details:");
            displayCustomer(existingCustomer);

            System.out.println("\nEnter new details (press Enter to keep current value):");

            System.out.print("Customer Name [" + existingCustomer.getCustomerName() + "]: ");
            String newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                newName = existingCustomer.getCustomerName();
            }

            System.out.print("Contact Number [" + existingCustomer.getContactNumber() + "]: ");
            String newContact = scanner.nextLine().trim();
            if (newContact.isEmpty()) {
                newContact = existingCustomer.getContactNumber();
            }

            System.out.print("Address [" + existingCustomer.getAddress() + "]: ");
            String newAddress = scanner.nextLine().trim();
            if (newAddress.isEmpty()) {
                newAddress = existingCustomer.getAddress();
            }

            Customer updatedCustomer = new Customer(custId, newName, newContact, newAddress);
            customerService.updateCustomer(updatedCustomer);

            System.out.println("\nCustomer updated successfully!");
            displayCustomer(updatedCustomer);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    // DELETE CUSTOMER
    private static void deleteCustomer() {
        System.out.println("DELETE CUSTOMER");
        System.out.println("---------------");

        try {
            int custId = getIntInput("Enter Customer ID to delete: ");

            Customer existingCustomer = customerService.getCustomerById(custId);
            if (existingCustomer == null) {
                System.out.println("Customer with ID " + custId + " not found.");
                return;
            }

            System.out.println("\nCustomer to be deleted:");
            displayCustomer(existingCustomer);

            System.out.print("\nAre you sure you want to delete this customer? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("y") || confirmation.equals("yes")) {
                boolean deleted = customerService.deleteCustomer(custId);

                if (deleted) {
                    System.out.println("Customer deleted successfully!");
                } else {
                    System.out.println("Failed to delete customer.");
                }
            } else {
                System.out.println("Delete operation cancelled.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    // UTILITY METHODS
    private static void displayCustomer(Customer customer) {
        System.out.println("  ID: " + customer.getCustId());
        System.out.println("  Name: " + customer.getCustomerName());
        System.out.println("  Contact: " + customer.getContactNumber());
        System.out.println("  Address: " + (customer.getAddress() != null ? customer.getAddress() : "N/A"));
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}