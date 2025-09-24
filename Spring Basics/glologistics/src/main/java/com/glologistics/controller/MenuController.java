package com.glologistics.controller;

import com.glologistics.model.*;
import com.glologistics.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import java.util.Scanner;

@Component
@Log4j2
public class MenuController implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    private final Scanner scanner = new Scanner(System.in);
    private Customer currentCustomer = null;

    @Override
    public void run(String... args) {
        log.info("GLOLogistics Inventory Management System started");
        displayMainMenu();
    }

    private void displayMainMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   GLOLogistics Inventory Management");
            System.out.println("========================================");
            System.out.println("1. Customer Login");
            System.out.println("2. Administrator Login");
            System.out.println("3. Exit");
            System.out.print("Select your role: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    customerLogin();
                    break;
                case 2:
                    adminMenu();
                    break;
                case 3:
                    System.out.println("Thank you for using GLOLogistics!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void customerLogin() {
        System.out.println("\n--- Customer Login ---");
        System.out.println("1. Existing Customer");
        System.out.println("2. New Customer Registration");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        if (choice == 1) {
            System.out.print("Enter Customer ID: ");
            int custId = getIntInput();
            currentCustomer = customerService.getCustomerById(custId);

            if (currentCustomer == null) {
                System.out.println("Customer not found!");
                return;
            }
        } else if (choice == 2) {
            currentCustomer = registerNewCustomer();
        } else {
            System.out.println("Invalid choice!");
            return;
        }

        customerMenu();
    }

    private Customer registerNewCustomer() {
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        long contact = getLongInput();
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(0, name, contact, address);
        customer = customerService.addCustomer(customer);
        System.out.println("Registration successful! Your Customer ID is: " + customer.getCustId());
        return customer;
    }

    private void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("Welcome, " + currentCustomer.getCustName());
            System.out.println("1. View All Products");
            System.out.println("2. Order Product");
            System.out.println("3. View My Orders");
            System.out.println("4. Delete Pending Order");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    viewAllProducts();
                    break;
                case 2:
                    orderProduct();
                    break;
                case 3:
                    viewMyOrders();
                    break;
                case 4:
                    deletePendingOrder();
                    break;
                case 5:
                    currentCustomer = null;
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void viewAllProducts() {
        List<Product> products = productService.getAllProducts();
        System.out.println("\n--- Available Products ---");
        System.out.printf("%-10s %-20s %-30s %-10s %-10s%n",
                "ID", "Name", "Description", "Price", "Stock");
        System.out.println("-".repeat(80));

        for (Product p : products) {
            System.out.printf("%-10d %-20s %-30s $%-9.2f %-10d%n",
                    p.getProductId(), p.getProductName(), p.getProductDescription(),
                    p.getProductPrice(), p.getProductQuantityInStock());
        }
    }

    private void orderProduct() {
        viewAllProducts();
        System.out.print("\nEnter Product ID to order: ");
        int productId = getIntInput();
        System.out.print("Enter Quantity: ");
        long quantity = getLongInput();

        Order order = orderService.generateOrder(currentCustomer.getCustId(), productId, quantity);

        if (order != null) {
            System.out.println("Order placed successfully!");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Total Amount: $" + order.getOrderTotalAmount());
            System.out.println("Status: " + order.getOrderStatus().getStatus());
        } else {
            System.out.println("Failed to place order. Product may be out of stock or invalid.");
        }
    }

    private void viewMyOrders() {
        List<Order> orders = customerService.viewAllOrders(currentCustomer.getCustId());

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n--- My Orders ---");
        System.out.printf("%-10s %-12s %-10s %-10s %-15s %-10s%n",
                "Order ID", "Date", "Product ID", "Quantity", "Total Amount", "Status");
        System.out.println("-".repeat(80));

        for (Order o : orders) {
            System.out.printf("%-10d %-12s %-10d %-10d $%-14.2f %-10s%n",
                    o.getOrderId(), o.getOrderDate(), o.getProductId(),
                    o.getOrderProductQuantity(), o.getOrderTotalAmount(),
                    o.getOrderStatus().getStatus());
        }
    }

    private void deletePendingOrder() {
        viewMyOrders();
        System.out.print("\nEnter Order ID to delete (only PENDING orders can be deleted): ");
        int orderId = getIntInput();

        if (orderService.deleteOrder(orderId, currentCustomer.getCustId())) {
            System.out.println("Order deleted successfully!");
        } else {
            System.out.println("Unable to delete order. It may not exist, not be yours, or already processed.");
        }
    }

    private void adminMenu() {
        System.out.print("\nEnter Admin Password: ");
        scanner.nextLine(); // Clear buffer
        String password = scanner.nextLine();

        if (!"admin123".equals(password)) {
            System.out.println("Invalid password!");
            return;
        }

        while (true) {
            System.out.println("\n--- Administrator Menu ---");
            System.out.println("1. Add New Product");
            System.out.println("2. Update Product");
            System.out.println("3. View All Products");
            System.out.println("4. View All Orders");
            System.out.println("5. View Pending Orders");
            System.out.println("6. Approve/Reject Orders");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    viewAllProducts();
                    break;
                case 4:
                    viewAllOrders();
                    break;
                case 5:
                    viewPendingOrders();
                    break;
                case 6:
                    processOrders();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void addNewProduct() {
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double price = getDoubleInput();
        System.out.print("Enter Initial Stock Quantity: ");
        int quantity = getIntInput();

        Product product = new Product(0, name, description, price, quantity);
        product = productService.addProduct(product);
        System.out.println("Product added successfully with ID: " + product.getProductId());
    }

    private void updateProduct() {
        viewAllProducts();
        System.out.print("\nEnter Product ID to update: ");
        int productId = getIntInput();

        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("Current Stock: " + product.getProductQuantityInStock());
        System.out.println("Current Price: $" + product.getProductPrice());

        System.out.print("Enter new stock quantity (-1 to skip): ");
        int newQuantity = getIntInput();
        System.out.print("Enter new price (-1 to skip): ");
        double newPrice = getDoubleInput();

        Double priceToUpdate = newPrice > 0 ? newPrice : null;
        productService.updateProduct(productId, newQuantity, priceToUpdate);
        System.out.println("Product updated successfully!");
    }

    private void viewAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n--- All Orders ---");
        System.out.printf("%-10s %-10s %-12s %-10s %-10s %-15s %-10s%n",
                "Order ID", "Cust ID", "Date", "Product ID", "Quantity", "Total", "Status");
        System.out.println("-".repeat(90));

        for (Order o : orders) {
            System.out.printf("%-10d %-10d %-12s %-10d %-10d $%-14.2f %-10s%n",
                    o.getOrderId(), o.getCustId(), o.getOrderDate(), o.getProductId(),
                    o.getOrderProductQuantity(), o.getOrderTotalAmount(),
                    o.getOrderStatus().getStatus());
        }
    }

    private void viewPendingOrders() {
        List<Order> orders = orderService.getPendingOrders();

        if (orders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        System.out.println("\n--- Pending Orders ---");
        System.out.printf("%-10s %-10s %-12s %-10s %-10s %-15s%n",
                "Order ID", "Cust ID", "Date", "Product ID", "Quantity", "Total");
        System.out.println("-".repeat(80));

        for (Order o : orders) {
            System.out.printf("%-10d %-10d %-12s %-10d %-10d $%-14.2f%n",
                    o.getOrderId(), o.getCustId(), o.getOrderDate(), o.getProductId(),
                    o.getOrderProductQuantity(), o.getOrderTotalAmount());
        }
    }

    private void processOrders() {
        viewPendingOrders();
        System.out.print("\nEnter Order ID to process: ");
        int orderId = getIntInput();

        System.out.println("1. Approve Order");
        System.out.println("2. Reject Order");
        System.out.print("Enter choice: ");
        int choice = getIntInput();

        if (choice == 1) {
            Order order = orderService.approveOrder(orderId);
            if (order != null) {
                System.out.println("Order approved successfully!");
            } else {
                System.out.println("Failed to approve order. May be out of stock or order not found.");
            }
        } else if (choice == 2) {
            Order order = orderService.rejectOrder(orderId);
            if (order != null) {
                System.out.println("Order rejected successfully!");
            } else {
                System.out.println("Failed to reject order. Order may not exist or already processed.");
            }
        }
    }

    private int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            log.error("Invalid integer input");
            return -1;
        }
    }

    private long getLongInput() {
        try {
            return scanner.nextLong();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            log.error("Invalid long input");
            return -1;
        }
    }

    private double getDoubleInput() {
        try {
            return scanner.nextDouble();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            log.error("Invalid double input");
            return -1;
        }
    }
}