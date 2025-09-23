package com.logistics.ui;

import com.logistics.application.ILogisticsService;
import com.logistics.models.*;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final ILogisticsService logisticsService;
    private final Scanner scanner;

    public ConsoleMenu(ILogisticsService logisticsService) {
        this.logisticsService = logisticsService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createShipmentMenu();
                    break;
                case 2:
                    viewShipmentsMenu();
                    break;
                case 3:
                    addCarrierMenu();
                    break;
                case 4:
                    viewCarriersMenu();
                    break;
                case 5:
                    addWarehouseMenu();
                    break;
                case 6:
                    viewWarehousesMenu();
                    break;
                case 7:
                    addCustomerMenu();
                    break;
                case 8:
                    viewCustomersMenu();
                    break;
                case 9:
                    assignCarrierToShipmentMenu();
                    break;
                case 10:
                    allocateWarehouseToShipmentMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting system");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("\n========== LOGISTICS MANAGEMENT SYSTEM ==========");
        System.out.println("1.  Create New Shipment");
        System.out.println("2.  View All Shipments");
        System.out.println("3.  Add New Carrier");
        System.out.println("4.  View All Carriers");
        System.out.println("5.  Add New Warehouse");
        System.out.println("6.  View All Warehouses");
        System.out.println("7.  Add New Customer");
        System.out.println("8.  View All Customers");
        System.out.println("9.  Assign Carrier to Shipment");
        System.out.println("10. Allocate Warehouse to Shipment");
        System.out.println("0.  Exit");
        System.out.print("Enter your choice: ");
    }

    private void createShipmentMenu() {
        System.out.println("\n--- Create New Shipment ---");

        System.out.println("Enter sender details:");
        System.out.print("Street: ");
        String senderStreet = scanner.nextLine();
        System.out.print("City: ");
        String senderCity = scanner.nextLine();
        System.out.print("Country: ");
        String senderCountry = scanner.nextLine();

        System.out.println("Enter receiver details:");
        System.out.print("Street: ");
        String receiverStreet = scanner.nextLine();
        System.out.print("City: ");
        String receiverCity = scanner.nextLine();
        System.out.print("Country: ");
        String receiverCountry = scanner.nextLine();

        System.out.print("Enter weight (kg): ");
        float weight = getFloatInput();

        Address sender = new Address(senderStreet, senderCity, senderCountry);
        Address receiver = new Address(receiverStreet, receiverCity, receiverCountry);

        Shipment shipment = logisticsService.createShipment(sender, receiver, weight);
        System.out.println("Shipment created successfully!");
        System.out.println("Estimated cost: $" + shipment.calculateShippingCost());
    }

    private void viewShipmentsMenu() {
        System.out.println("\n--- All Shipments ---");
        List<Shipment> shipments = logisticsService.getAllShipments();

        if (shipments.isEmpty()) {
            System.out.println("No shipments found.");
        } else {
            for (Shipment s : shipments) {
                System.out.println(s);
                System.out.println("  From: " + s.getSender());
                System.out.println("  To: " + s.getReceiver());
                System.out.println("  Cost: $" + s.calculateShippingCost());
            }
        }
    }

    private void addCarrierMenu() {
        System.out.println("\n--- Add New Carrier ---");

        System.out.print("Carrier name: ");
        String carrierName = scanner.nextLine();

        System.out.println("Enter contact details:");
        System.out.print("Contact name: ");
        String contactName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        Contact contact = new Contact(contactName, email, phone);
        Carrier carrier = new Carrier(carrierName, contact);

        System.out.println("Enter shipping rates:");
        System.out.print("Ground rate ($/kg): ");
        float ground = getFloatInput();
        System.out.print("Air rate ($/kg): ");
        float air = getFloatInput();
        System.out.print("Ocean rate ($/kg): ");
        float ocean = getFloatInput();

        carrier.setRates(new Rates(ground, air, ocean));

        // Now properly saves to database
        logisticsService.addCarrier(carrier);
    }

    private void viewCarriersMenu() {
        System.out.println("\n--- All Carriers ---");
        List<Carrier> carriers = logisticsService.getAllCarriers();

        if (carriers.isEmpty()) {
            System.out.println("No carriers found.");
        } else {
            for (int i = 0; i < carriers.size(); i++) {
                Carrier c = carriers.get(i);
                System.out.println((i + 1) + ". " + c);
                if (c.getRates() != null) {
                    System.out.println("   Rates: " + c.getRates());
                }
            }
        }
    }

    private void addWarehouseMenu() {
        System.out.println("\n--- Add New Warehouse ---");

        System.out.print("Latitude: ");
        float lat = getFloatInput();
        System.out.print("Longitude: ");
        float lon = getFloatInput();
        System.out.print("Capacity: ");
        int capacity = getIntInput();

        Location location = new Location(lat, lon);
        Warehouse warehouse = new Warehouse(location, capacity);

        // Now properly saves to database
        logisticsService.addWarehouse(warehouse);
    }

    private void viewWarehousesMenu() {
        System.out.println("\n--- All Warehouses ---");
        List<Warehouse> warehouses = logisticsService.getAllWarehouses();

        if (warehouses.isEmpty()) {
            System.out.println("No warehouses found.");
        } else {
            for (int i = 0; i < warehouses.size(); i++) {
                Warehouse w = warehouses.get(i);
                System.out.println((i + 1) + ". " + w);
            }
        }
    }

    private void addCustomerMenu() {
        System.out.println("\n--- Add New Customer ---");

        System.out.print("Customer name: ");
        String customerName = scanner.nextLine();

        System.out.println("Enter contact details:");
        System.out.print("Contact name: ");
        String contactName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        Contact contact = new Contact(contactName, email, phone);
        Customer customer = new Customer(customerName, contact);

        logisticsService.addCustomer(customer);
        System.out.println("Customer added successfully!");
    }

    private void viewCustomersMenu() {
        System.out.println("\n--- All Customers ---");
        List<Customer> customers = logisticsService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer c : customers) {
                System.out.println(c);
            }
        }
    }

    private void assignCarrierToShipmentMenu() {
        System.out.println("\n--- Assign Carrier to Shipment ---");

        List<Shipment> shipments = logisticsService.getAllShipments();
        List<Carrier> carriers = logisticsService.getAllCarriers();

        if (shipments.isEmpty() || carriers.isEmpty()) {
            System.out.println("No shipments or carriers available.");
            return;
        }

        System.out.println("Available Shipments:");
        for (int i = 0; i < shipments.size(); i++) {
            Shipment s = shipments.get(i);
            System.out.println((i + 1) + ". Shipment #" + s.getId() + " - " + s.getWeight() + "kg - Status: " + s.getStatus());
            System.out.println("   From: " + s.getSender());
            System.out.println("   To: " + s.getReceiver());
            if (s.getCarrier() != null) {
                System.out.println("   Current Carrier: " + s.getCarrier().getName());
            }
        }

        System.out.print("Select shipment number (1-" + shipments.size() + "): ");
        int shipmentIndex = getIntInput() - 1;

        if (shipmentIndex < 0 || shipmentIndex >= shipments.size()) {
            System.out.println("Invalid shipment selection.");
            return;
        }

        System.out.println("\nAvailable Carriers:");
        for (int i = 0; i < carriers.size(); i++) {
            Carrier c = carriers.get(i);
            System.out.println((i + 1) + ". " + c.getName());
            if (c.getRates() != null) {
                System.out.println("   Rates: " + c.getRates());
            }
        }

        System.out.print("Select carrier number (1-" + carriers.size() + "): ");
        int carrierIndex = getIntInput() - 1;

        if (carrierIndex >= 0 && carrierIndex < carriers.size()) {
            logisticsService.assignCarrier(shipments.get(shipmentIndex), carriers.get(carrierIndex));
            System.out.println("Carrier successfully assigned!");
        } else {
            System.out.println("Invalid carrier selection.");
        }
    }

    private void allocateWarehouseToShipmentMenu() {
        System.out.println("\n--- Allocate Warehouse to Shipment ---");

        List<Shipment> shipments = logisticsService.getAllShipments();
        List<Warehouse> warehouses = logisticsService.getAllWarehouses();

        if (shipments.isEmpty() || warehouses.isEmpty()) {
            System.out.println("No shipments or warehouses available.");
            return;
        }

        System.out.println("Available Shipments:");
        for (int i = 0; i < shipments.size(); i++) {
            Shipment s = shipments.get(i);
            System.out.println((i + 1) + ". Shipment #" + s.getId() + " - " + s.getWeight() + "kg - Status: " + s.getStatus());
            System.out.println("   From: " + s.getSender());
            System.out.println("   To: " + s.getReceiver());
            if (s.getWarehouse() != null) {
                System.out.println("   Current Warehouse: " + s.getWarehouse());
            }
        }

        System.out.print("Select shipment number (1-" + shipments.size() + "): ");
        int shipmentIndex = getIntInput() - 1;

        if (shipmentIndex < 0 || shipmentIndex >= shipments.size()) {
            System.out.println("Invalid shipment selection.");
            return;
        }

        System.out.println("\nAvailable Warehouses:");
        for (int i = 0; i < warehouses.size(); i++) {
            Warehouse w = warehouses.get(i);
            System.out.println((i + 1) + ". " + w);
            System.out.println("   Current inventory items: " + w.getInventory().size());
        }

        System.out.print("Select warehouse number (1-" + warehouses.size() + "): ");
        int warehouseIndex = getIntInput() - 1;

        if (warehouseIndex >= 0 && warehouseIndex < warehouses.size()) {
            logisticsService.allocateWarehouse(shipments.get(shipmentIndex), warehouses.get(warehouseIndex));
            System.out.println("Warehouse successfully allocated!");
        } else {
            System.out.println("Invalid warehouse selection.");
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private float getFloatInput() {
        while (true) {
            try {
                float value = Float.parseFloat(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
