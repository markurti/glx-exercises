package com.glologistics.controller;

import com.glologistics.model.*;
import com.glologistics.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Menu-driven controller for console interaction
 * Implements CommandLineRunner to start the console application
 */
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
        log.info("GLOLogistics Inventory Management System started with JPA");
        initializeSampleData();
        displayMainMenu();
    }

    /**
     * Initialize sample data if database is empty
     */
    private void initializeSampleData() {
        try {
            // Add sample products if none exist
            if (productService.getAllProducts().isEmpty()) {
                log.info("Initializing sample products");
                productService.addProduct(new Product(null, "Laptop", "High-performance laptop",
                        new BigDecimal("999.99"), 50));
                productService.addProduct(new Product(null, "Mouse", "Wireless mouse",
                        new BigDecimal("25.99"), 200));
                productService.addProduct(new Product(null, "Keyboard", "Mechanical keyboard",
                        new BigDecimal("79.99"), 150));
                productService.addProduct(new Product(null, "Monitor", "27-inch LED monitor",
                        new BigDecimal("299.99"), 75));
            }

            // Add sample customers if none exist
            if (customerService.getAllCustomers().isEmpty()) {
                log.info("Initializing sample customers");
                customerService.addCustomer(new Customer(null, "John Doe", "1234567890", "123 Main St"));
                customerService.addCustomer(new Customer(null, "Jane Smith", "9876543210", "456 Oak Ave"));
            }
        } catch (Exception e) {
            log.error("Error initializing sample data: {}", e.getMessage());
        }
    }

    /**
     * Display main menu and handle user selection
     */
    private void displayMainMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   GLOLogistics Inventory Management");
            System.out.println("        (JPA + PostgreSQL)");
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

    /**
     * Handle customer login process
     */
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

        if (currentCustomer != null) {
            customerMenu();
        }
    }

    /**
     * Register a new customer
     * @return Newly registered customer
     */
    private Customer registerNewCustomer() {
        try {
            scanner.nextLine(); // Clear buffer
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                return null;
            }

            System.out.print("Enter Contact Number: ");
            String contact = scanner.nextLine().trim();

            if (contact.isEmpty()) {
                System.out.println("Contact cannot be empty!");
                return null;
            }

            System.out.print("Enter Address: ");
            String address = scanner.nextLine().trim();

            if (address.isEmpty()) {
                System.out.println("Address cannot be empty!");
                return null;
            }

            Customer customer = new Customer(null, name, contact, address);
            customer = customerService.addCustomer(customer);
            System.out.println("Registration successful! Your Customer ID is: " + customer.getCustId());
            return customer;
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
            log.error("Customer registration error: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Display customer menu and handle customer operations
     */
    private void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("Welcome, " + currentCustomer.getCustName());
            System.out.println("Customer ID: " + currentCustomer.getCustId());
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

    /**
     * Display all available products
     */
    private void viewAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();

            if (products.isEmpty()) {
                System.out.println("No products available.");
                return;
            }

            System.out.println("\n--- Available Products ---");
            System.out.printf("%-10s %-20s %-30s %-12s %-10s%n",
                    "ID", "Name", "Description", "Price ($)", "Stock");
            System.out.println("-".repeat(85));

            for (Product p : products) {
                System.out.printf("%-10d %-20s %-30s %-12.2f %-10d%n",
                        p.getProductId(),
                        truncate(p.getProductName(), 20),
                        truncate(p.getProductDescription(), 30),
                        p.getProductPrice(),
                        p.getProductQuantityInStock());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
            log.error("Error in viewAllProducts: {}", e.getMessage());
        }
    }

    /**
     * Handle product ordering by customer
     */
    private void orderProduct() {
        try {
            viewAllProducts();

            System.out.print("\nEnter Product ID to order: ");
            int productId = getIntInput();

            if (productId <= 0) {
                System.out.println("Invalid Product ID!");
                return;
            }

            // Validate product exists
            Product product = productService.getProductById(productId);
            if (product == null) {
                System.out.println("Product not found!");
                return;
            }

            System.out.println("Selected Product: " + product.getProductName());
            System.out.println("Available Stock: " + product.getProductQuantityInStock());
            System.out.println("Price: $" + product.getProductPrice());

            System.out.print("Enter Quantity: ");
            long quantity = getLongInput();

            if (quantity <= 0) {
                System.out.println("Invalid quantity!");
                return;
            }

            if (quantity > product.getProductQuantityInStock()) {
                System.out.println("Insufficient stock! Available: " + product.getProductQuantityInStock());
                return;
            }

            Order order = orderService.generateOrder(currentCustomer.getCustId(), productId, quantity);

            if (order != null) {
                System.out.println("\n=== Order Placed Successfully! ===");
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Product: " + product.getProductName());
                System.out.println("Quantity: " + order.getOrderProductQuantity());
                System.out.println("Total Amount: $" + order.getOrderTotalAmount());
                System.out.println("Order Date: " + order.getOrderDate());
                System.out.println("Status: " + order.getOrderStatus().getStatus());
            } else {
                System.out.println("Failed to place order. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
            log.error("Error in orderProduct: {}", e.getMessage());
        }
    }

    /**
     * Display customer's orders
     */
    private void viewMyOrders() {
        try {
            List<Order> orders = customerService.viewAllOrders(currentCustomer.getCustId());

            if (orders.isEmpty()) {
                System.out.println("You have no orders.");
                return;
            }

            System.out.println("\n--- My Orders ---");
            System.out.printf("%-10s %-12s %-10s %-20s %-10s %-15s %-12s%n",
                    "Order ID", "Date", "Product ID", "Product Name", "Quantity", "Total Amount", "Status");
            System.out.println("-".repeat(95));

            for (Order o : orders) {
                System.out.printf("%-10d %-12s %-10d %-20s %-10d $%-14.2f %-12s%n",
                        o.getOrderId(),
                        o.getOrderDate(),
                        o.getProduct().getProductId(),
                        truncate(o.getProduct().getProductName(), 20),
                        o.getOrderProductQuantity(),
                        o.getOrderTotalAmount(),
                        o.getOrderStatus().getStatus());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving orders: " + e.getMessage());
            log.error("Error in viewMyOrders: {}", e.getMessage());
        }
    }

    /**
     * Allow customer to delete pending orders
     */
    private void deletePendingOrder() {
        try {
            // Show only pending orders
            List<Order> orders = customerService.viewAllOrders(currentCustomer.getCustId());
            List<Order> pendingOrders = orders.stream()
                    .filter(o -> o.getOrderStatus() == OrderStatus.PENDING)
                    .toList();

            if (pendingOrders.isEmpty()) {
                System.out.println("You have no pending orders to delete.");
                return;
            }

            System.out.println("\n--- Your Pending Orders ---");
            System.out.printf("%-10s %-12s %-20s %-10s %-15s%n",
                    "Order ID", "Date", "Product Name", "Quantity", "Total Amount");
            System.out.println("-".repeat(70));

            for (Order o : pendingOrders) {
                System.out.printf("%-10d %-12s %-20s %-10d $%-14.2f%n",
                        o.getOrderId(),
                        o.getOrderDate(),
                        truncate(o.getProduct().getProductName(), 20),
                        o.getOrderProductQuantity(),
                        o.getOrderTotalAmount());
            }

            System.out.print("\nEnter Order ID to delete: ");
            int orderId = getIntInput();

            if (orderId <= 0) {
                System.out.println("Invalid Order ID!");
                return;
            }

            if (orderService.deleteOrder(orderId, currentCustomer.getCustId())) {
                System.out.println("Order deleted successfully!");
            } else {
                System.out.println("Unable to delete order. Please check the Order ID and try again.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
            log.error("Error in deletePendingOrder: {}", e.getMessage());
        }
    }

    /**
     * Handle administrator menu
     */
    private void adminMenu() {
        System.out.print("\nEnter Admin Password: ");
        scanner.nextLine(); // Clear buffer
        String password = scanner.nextLine().trim();

        if (!"admin123".equals(password)) {
            System.out.println("Invalid password!");
            return;
        }

        while (true) {
            System.out.println("\n--- Administrator Menu ---");
            System.out.println("1. Add New Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View All Products");
            System.out.println("5. View All Orders");
            System.out.println("6. View Pending Orders");
            System.out.println("7. Approve/Reject Orders");
            System.out.println("8. View All Customers");
            System.out.println("9. Back to Main Menu");
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
                    deleteProduct();
                    break;
                case 4:
                    viewAllProducts();
                    break;
                case 5:
                    viewAllOrders();
                    break;
                case 6:
                    viewPendingOrders();
                    break;
                case 7:
                    processOrders();
                    break;
                case 8:
                    viewAllCustomers();
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    /**
     * Add a new product (admin function)
     */
    private void addNewProduct() {
        try {
            scanner.nextLine(); // Clear buffer

            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Product name cannot be empty!");
                return;
            }

            System.out.print("Enter Product Description: ");
            String description = scanner.nextLine().trim();

            System.out.print("Enter Product Price: ");
            BigDecimal price = getBigDecimalInput();
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Invalid price!");
                return;
            }

            System.out.print("Enter Initial Stock Quantity: ");
            int quantity = getIntInput();
            if (quantity < 0) {
                System.out.println("Invalid quantity!");
                return;
            }

            Product product = new Product(null, name, description, price, quantity);
            product = productService.addProduct(product);

            if (product != null) {
                System.out.println("Product added successfully!");
                System.out.println("Product ID: " + product.getProductId());
                System.out.println("Name: " + product.getProductName());
                System.out.println("Price: $" + product.getProductPrice());
                System.out.println("Stock: " + product.getProductQuantityInStock());
            } else {
                System.out.println("Failed to add product!");
            }
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
            log.error("Error in addNewProduct: {}", e.getMessage());
        }
    }

    /**
     * Update an existing product (admin function)
     */
    private void updateProduct() {
        try {
            viewAllProducts();

            System.out.print("\nEnter Product ID to update: ");
            int productId = getIntInput();

            if (productId <= 0) {
                System.out.println("Invalid Product ID!");
                return;
            }

            Product product = productService.getProductById(productId);
            if (product == null) {
                System.out.println("Product not found!");
                return;
            }

            System.out.println("\nCurrent Product Details:");
            System.out.println("Name: " + product.getProductName());
            System.out.println("Description: " + product.getProductDescription());
            System.out.println("Price: $" + product.getProductPrice());
            System.out.println("Stock: " + product.getProductQuantityInStock());

            System.out.print("\nEnter new stock quantity (-1 to skip): ");
            int newQuantity = getIntInput();

            System.out.print("Enter new price (-1 to skip): ");
            BigDecimal newPrice = getBigDecimalInput();

            Integer quantityToUpdate = newQuantity >= 0 ? newQuantity : null;
            BigDecimal priceToUpdate = (newPrice != null && newPrice.compareTo(BigDecimal.ZERO) > 0) ? newPrice : null;

            Product updatedProduct = productService.updateProduct(productId, quantityToUpdate, priceToUpdate);

            if (updatedProduct != null) {
                System.out.println("Product updated successfully!");
                System.out.println("Updated Stock: " + updatedProduct.getProductQuantityInStock());
                System.out.println("Updated Price: $" + updatedProduct.getProductPrice());
            } else {
                System.out.println("Failed to update product!");
            }
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
            log.error("Error in updateProduct: {}", e.getMessage());
        }
    }

    /**
     * Delete a product (admin function)
     */
    private void deleteProduct() {
        try {
            viewAllProducts();

            System.out.print("\nEnter Product ID to delete: ");
            int productId = getIntInput();

            if (productId <= 0) {
                System.out.println("Invalid Product ID!");
                return;
            }

            Product product = productService.getProductById(productId);
            if (product == null) {
                System.out.println("Product not found!");
                return;
            }

            System.out.println("Product to delete: " + product.getProductName());
            System.out.print("Are you sure you want to delete this product? (y/n): ");
            scanner.nextLine(); // Clear buffer
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation) || "yes".equals(confirmation)) {
                if (productService.deleteProduct(productId)) {
                    System.out.println("Product deleted successfully!");
                } else {
                    System.out.println("Failed to delete product!");
                }
            } else {
                System.out.println("Product deletion cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
            log.error("Error in deleteProduct: {}", e.getMessage());
        }
    }

    /**
     * View all orders (admin function)
     */
    private void viewAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();

            if (orders.isEmpty()) {
                System.out.println("No orders found.");
                return;
            }

            System.out.println("\n--- All Orders ---");
            System.out.printf("%-10s %-10s %-12s %-20s %-10s %-15s %-12s%n",
                    "Order ID", "Cust ID", "Date", "Product Name", "Quantity", "Total", "Status");
            System.out.println("-".repeat(95));

            for (Order o : orders) {
                System.out.printf("%-10d %-10d %-12s %-20s %-10d $%-14.2f %-12s%n",
                        o.getOrderId(),
                        o.getCustomer().getCustId(),
                        o.getOrderDate(),
                        truncate(o.getProduct().getProductName(), 20),
                        o.getOrderProductQuantity(),
                        o.getOrderTotalAmount(),
                        o.getOrderStatus().getStatus());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving orders: " + e.getMessage());
            log.error("Error in viewAllOrders: {}", e.getMessage());
        }
    }

    /**
     * View pending orders (admin function)
     */
    private void viewPendingOrders() {
        try {
            List<Order> orders = orderService.getPendingOrders();

            if (orders.isEmpty()) {
                System.out.println("No pending orders.");
                return;
            }

            System.out.println("\n--- Pending Orders ---");
            System.out.printf("%-10s %-10s %-12s %-20s %-10s %-15s%n",
                    "Order ID", "Cust ID", "Date", "Product Name", "Quantity", "Total");
            System.out.println("-".repeat(80));

            for (Order o : orders) {
                System.out.printf("%-10d %-10d %-12s %-20s %-10d $%-14.2f%n",
                        o.getOrderId(),
                        o.getCustomer().getCustId(),
                        o.getOrderDate(),
                        truncate(o.getProduct().getProductName(), 20),
                        o.getOrderProductQuantity(),
                        o.getOrderTotalAmount());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving pending orders: " + e.getMessage());
            log.error("Error in viewPendingOrders: {}", e.getMessage());
        }
    }

    /**
     * Process orders - approve or reject (admin function)
     */
    private void processOrders() {
        try {
            viewPendingOrders();

            List<Order> pendingOrders = orderService.getPendingOrders();
            if (pendingOrders.isEmpty()) {
                return;
            }

            System.out.print("\nEnter Order ID to process: ");
            int orderId = getIntInput();

            if (orderId <= 0) {
                System.out.println("Invalid Order ID!");
                return;
            }

            // Verify it's a pending order
            boolean isValidPendingOrder = pendingOrders.stream()
                    .anyMatch(o -> o.getOrderId().equals(orderId));

            if (!isValidPendingOrder) {
                System.out.println("Order ID not found in pending orders!");
                return;
            }

            System.out.println("1. Approve Order");
            System.out.println("2. Reject Order");
            System.out.print("Enter choice: ");
            int choice = getIntInput();

            Order processedOrder = null;

            if (choice == 1) {
                processedOrder = orderService.approveOrder(orderId);
                if (processedOrder != null) {
                    System.out.println("Order approved successfully!");
                    System.out.println("Stock has been updated for product: " +
                            processedOrder.getProduct().getProductName());
                } else {
                    System.out.println("Failed to approve order. May be out of stock.");
                }
            } else if (choice == 2) {
                processedOrder = orderService.rejectOrder(orderId);
                if (processedOrder != null) {
                    System.out.println("Order rejected successfully!");
                } else {
                    System.out.println("Failed to reject order.");
                }
            } else {
                System.out.println("Invalid choice!");
            }
        } catch (Exception e) {
            System.out.println("Error processing order: " + e.getMessage());
            log.error("Error in processOrders: {}", e.getMessage());
        }
    }

    /**
     * View all customers (admin function)
     */
    private void viewAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();

            if (customers.isEmpty()) {
                System.out.println("No customers found.");
                return;
            }

            System.out.println("\n--- All Customers ---");
            System.out.printf("%-10s %-20s %-15s %-30s%n",
                    "Cust ID", "Name", "Contact", "Address");
            System.out.println("-".repeat(75));

            for (Customer c : customers) {
                System.out.printf("%-10d %-20s %-15s %-30s%n",
                        c.getCustId(),
                        truncate(c.getCustName(), 20),
                        c.getCustContact(),
                        truncate(c.getCustAdd(), 30));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
            log.error("Error in viewAllCustomers: {}", e.getMessage());
        }
    }

    // =================== UTILITY METHODS ===================

    /**
     * Get integer input with error handling
     * @return Valid integer or -1 if invalid
     */
    private int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            log.debug("Invalid integer input");
            return -1;
        }
    }

    /**
     * Get long input with error handling
     * @return Valid long or -1 if invalid
     */
    private long getLongInput() {
        try {
            return scanner.nextLong();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            log.debug("Invalid long input");
            return -1;
        }
    }

    /**
     * Get BigDecimal input with error handling
     * @return Valid BigDecimal or null if invalid
     */
    private BigDecimal getBigDecimalInput() {
        try {
            double value = scanner.nextDouble();
            return BigDecimal.valueOf(value);
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            log.debug("Invalid decimal input");
            return null;
        }
    }

    /**
     * Truncate string to specified length with ellipsis
     * @param str String to truncate
     * @param maxLength Maximum length
     * @return Truncated string
     */
    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}