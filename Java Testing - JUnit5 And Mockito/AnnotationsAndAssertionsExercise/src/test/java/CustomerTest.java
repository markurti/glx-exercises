import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CustomerTest {
    private CustomerService customerService;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        customerService = new CustomerServiceImpl(customerRepository);

        // Create test customer data
        testCustomer = new Customer(1, "John Doe", "0749237812", "Alba Iulia str. A. Muresanu nr. 56");
    }

    // ------------- CREATE OPERATION TESTS ------------- //

    @Test
    @DisplayName("Test creating a valid customer")
    void testCreateCustomer() {
        // Arrange
        Customer newCustomer = new Customer(2, "Jane Smith", "0738592817", "Cluj Napoca str. B. Cogalniceanu nr. 2");

        // Act
        customerService.addCustomer(newCustomer);

        // Assert - Verify customer was created
        Customer receivedCustomer = customerService.getCustomerById(2);
        assertNotNull(receivedCustomer);
        assertEquals(2, receivedCustomer.getCustId());
    }

    @Test
    @DisplayName("Validate the accuracy of customer details")
    void testValidateCustomer() {
        // Arrange
        Customer customerToCreate = new Customer(3, "Bob Johnson", "07893748291", "Timisoara str. A. Alex nr. 45");

        // Act
        customerService.addCustomer(customerToCreate);

        // Assert - Validate created customer data
        Customer createdCustomer = customerService.getCustomerById(3);
        assertNotNull(createdCustomer);
        assertEquals(3, createdCustomer.getCustId());
        assertEquals("Bob Johnson", createdCustomer.getCustomerName());
        assertEquals("07893748291", createdCustomer.getContactNumber());
        assertEquals("Timisoara str. A. Alex nr. 45", createdCustomer.getAddress());
    }

    @Test
    @DisplayName("Test customer creation validation - null customer")
    void testCustomerCreationValidationNullCustomer() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.addCustomer(null);
        });
        assertEquals("Customer cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test customer creation validation - empty customer name")
    void testCustomerCreationValidationEmptyCustomerName() {
        // Arrange
        Customer invalidCustomer = new Customer(5, "", "555-0000", "Some Address");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(invalidCustomer)
        );
        assertEquals("Customer name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Test customer creation validation - null contact number")
    void testCustomerCreationValidationNullContactNumber() {
        // Arrange
        Customer invalidCustomer = new Customer(6, "Valid Name", null, "Some Address");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(invalidCustomer)
        );
        assertEquals("Contact number cannot be null or empty", exception.getMessage());
    }

    // ------------- READ OPERATION TESTS ------------- //

    @Test
    @DisplayName("Test retrieving customer by valid ID")
    void testRetrieveCustomerById() {
        // Arrange
        customerService.addCustomer(testCustomer);

        // Act
        Customer retrievedCustomer = customerService.getCustomerById(1);

        // Assert
        assertNotNull(retrievedCustomer);
        assertEquals(testCustomer.getCustId(), retrievedCustomer.getCustId());
        assertEquals(testCustomer.getCustomerName(), retrievedCustomer.getCustomerName());
        assertEquals(testCustomer.getContactNumber(), retrievedCustomer.getContactNumber());
        assertEquals(testCustomer.getAddress(), retrievedCustomer.getAddress());
    }

    @Test
    @DisplayName("Assert accuracy of retrieved customer information")
    void testRetrieveCustomerByIdAccuracy() {
        // Arrange
        Customer expectedCustomer = new Customer(10, "Test User", "0772839172", "Test Address 123");
        customerService.addCustomer(expectedCustomer);

        // Act
        Customer actualCustomer = customerService.getCustomerById(10);

        // Assert - Detailed validation of each field
        assertNotNull(actualCustomer);
        assertEquals(10, actualCustomer.getCustId(), "Customer ID should match");
        assertEquals("Test User", actualCustomer.getCustomerName(), "Customer name should match");
        assertEquals("0772839172", actualCustomer.getContactNumber(), "Contact number should match");
        assertEquals("Test Address 123", actualCustomer.getAddress(), "Address should match");
    }

    @Test
    @DisplayName("Test retrieving non-existent customer returns null")
    void testGetCustomerByIdNonExistentCustomer() {
        // Act
        Customer retrievedCustomer = customerService.getCustomerById(999);

        // Assert
        assertNull(retrievedCustomer);
    }

    @Test
    @DisplayName("Test retrieving customer with invalid ID throws exception")
    void testGetCustomerByIdInvalidCustomerId() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.getCustomerById(0)
        );
        assertEquals("Customer id must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Test retrieving all customers")
    void testGetAllCustomers() {
        // Arrange
        Customer customer1 = new Customer(1, "John Doe", "123-456-7890", "123 Main St");
        Customer customer2 = new Customer(2, "Jane Smith", "987-654-3210", "456 Oak Ave");
        Customer customer3 = new Customer(3, "Bob Johnson", "555-123-4567", "789 Pine St");

        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);
        customerService.addCustomer(customer3);

        // Act
        List<Customer> retrievedCustomers = customerService.getAllCustomers();

        // Assert
        assertNotNull(retrievedCustomers);
        assertEquals(3, retrievedCustomers.size());
        assertTrue(retrievedCustomers.contains(customer1));
        assertTrue(retrievedCustomers.contains(customer2));
        assertTrue(retrievedCustomers.contains(customer3));
    }

    @Test
    @DisplayName("Assert accuracy of all retrieved customers data")
    void testGetAllCustomersRetrievedDataAccuracy() {
        // Arrange
        Customer customer1 = new Customer(11, "Alice Brown", "111-111-1111", "111 First St");
        Customer customer2 = new Customer(12, "Charlie Davis", "222-222-2222", "222 Second St");

        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);

        // Act
        List<Customer> actualCustomers = customerService.getAllCustomers();

        // Assert
        assertEquals(2, actualCustomers.size());

        // Find and validate first customer
        Customer actualCustomer1 = null;
        for (Customer c : actualCustomers) {
            if (c.getCustId() == 11) {
                actualCustomer1 = c;
                break;
            }
        }
        assertNotNull(actualCustomer1);
        assertEquals("Alice Brown", actualCustomer1.getCustomerName());
        assertEquals("111-111-1111", actualCustomer1.getContactNumber());
        assertEquals("111 First St", actualCustomer1.getAddress());

        // Find and validate second customer
        Customer actualCustomer2 = null;
        for (Customer c : actualCustomers) {
            if (c.getCustId() == 12) {
                actualCustomer2 = c;
                break;
            }
        }
        assertNotNull(actualCustomer2);
        assertEquals("Charlie Davis", actualCustomer2.getCustomerName());
        assertEquals("222-222-2222", actualCustomer2.getContactNumber());
        assertEquals("222 Second St", actualCustomer2.getAddress());
    }

    @Test
    @DisplayName("Test retrieving all customers when repository is empty")
    void testGetAllCustomersEmptyRepository() {
        // Act
        List<Customer> retrievedCustomers = customerService.getAllCustomers();

        // Assert
        assertNotNull(retrievedCustomers);
        assertTrue(retrievedCustomers.isEmpty());
        assertEquals(0, retrievedCustomers.size());
    }

    // ==================== CREATE AND READ INTEGRATION TESTS ====================

    @Test
    @DisplayName("Test create customer and then read the created customer")
    void testCreateThenReadCustomer() {
        // Arrange
        Customer customerToCreate = new Customer(100, "Integration Test User", "999-888-7777", "Integration Test Address");

        // Act
        customerService.addCustomer(customerToCreate);

        Customer retrievedCustomer = customerService.getCustomerById(100);

        // Assert
        assertNotNull(retrievedCustomer);
        assertEquals(customerToCreate.getCustId(), retrievedCustomer.getCustId());
        assertEquals(customerToCreate.getCustomerName(), retrievedCustomer.getCustomerName());
        assertEquals(customerToCreate.getContactNumber(), retrievedCustomer.getContactNumber());
        assertEquals(customerToCreate.getAddress(), retrievedCustomer.getAddress());
    }

    @Test
    @DisplayName("Test multiple customers creation and reading")
    void testCreateAndReadMultipleCustomers() {
        // Arrange
        Customer customer1 = new Customer(201, "First Customer", "111-000-0001", "Address 1");
        Customer customer2 = new Customer(202, "Second Customer", "111-000-0002", "Address 2");
        Customer customer3 = new Customer(203, "Third Customer", "111-000-0003", "Address 3");

        // Act
        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);
        customerService.addCustomer(customer3);

        Customer retrieved1 = customerService.getCustomerById(201);
        Customer retrieved2 = customerService.getCustomerById(202);
        Customer retrieved3 = customerService.getCustomerById(203);
        List<Customer> allCustomers = customerService.getAllCustomers();

        // Assert
        assertNotNull(retrieved1);
        assertEquals("First Customer", retrieved1.getCustomerName());

        assertNotNull(retrieved2);
        assertEquals("Second Customer", retrieved2.getCustomerName());

        assertNotNull(retrieved3);
        assertEquals("Third Customer", retrieved3.getCustomerName());

        assertEquals(3, allCustomers.size());
        assertTrue(allCustomers.contains(customer1));
        assertTrue(allCustomers.contains(customer2));
        assertTrue(allCustomers.contains(customer3));
    }
}
