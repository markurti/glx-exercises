import org.example.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


public class CustomerTest {
    private CustomerServiceImpl customerService;
    private static TestDatabaseConnection testDatabaseConnection;

    @BeforeAll
    static void setUpTestDatabase() {
        // Assumption: test database is available for testing
        testDatabaseConnection = new TestDatabaseConnection();

        // Verify database connection is working before running tests
        try (Connection connection = testDatabaseConnection.getConnection()) {
            assumeTrue(connection != null, "Database connection must be available for tests to run");
            assumeTrue(!connection.isClosed(), "Database connection must be active");
            System.out.println("Successfully connected to TestCustomerDatabase");
        } catch (SQLException e) {
            assumeTrue(false, "Cannot connect to TestCustomerDatabase: " + e.getMessage());
        }
    }

    @BeforeEach
    void setUp() throws SQLException {
        // Assumption: Database connection is working and application is connected to the database
        assumeTrue(testDatabaseConnection != null, "Test database connection must be initialized");

        // Initialize repository and service with test database
        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl(testDatabaseConnection);
        customerService = new CustomerServiceImpl(customerRepository);

        // Clear any existing test data to ensure clean state
        testDatabaseConnection.clearTable();

        // Assumption: Test database should be pre-populated with test data
        populateTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Clean up test data after each test to maintain isolation
        if (testDatabaseConnection != null) {
            testDatabaseConnection.clearTable();
        }
    }

    private void populateTestData() {
        // Pre-populate test database with sample data as per assumptions
        Customer prePopulated1 = new Customer(100, "John Smith", "555-0001", "100 Main St");
        Customer prePopulated2 = new Customer(200, "Jane Doe", "555-0002", "200 Oak Ave");
        Customer prePopulated3 = new Customer(300, "Bob Johnson", "555-0003", "300 Pine St");

        customerService.addCustomer(prePopulated1);
        customerService.addCustomer(prePopulated2);
        customerService.addCustomer(prePopulated3);
    }

    // ------------- CREATE CUSTOMER TESTS ------------- //

    @Test
    @DisplayName("Test creating a customer with valid data")
    void testCreateCustomer_ValidData_Success() {
        // Assumption: Database is connected and ready for CRUD operations
        assumeTrue(customerService != null, "Customer service must be available");

        // Arrange
        Customer newCustomer = new Customer(1001, "Alice Johnson", "555-1001", "1001 Elm St");

        // Act
        assertDoesNotThrow(() -> customerService.addCustomer(newCustomer));

        // Assert - Verify customer was created in database
        Customer retrievedCustomer = customerService.getCustomerById(1001);
        assertNotNull(retrievedCustomer, "Customer should be created in database");
        assertEquals(1001, retrievedCustomer.getCustId(), "Customer ID should match");
        assertEquals("Alice Johnson", retrievedCustomer.getCustomerName(), "Customer name should match");
        assertEquals("555-1001", retrievedCustomer.getContactNumber(), "Contact number should match");
        assertEquals("1001 Elm St", retrievedCustomer.getAddress(), "Address should match");
    }
    @Test
    @DisplayName("Test creating a customer with invalid data - null name")
    void testCreateCustomer_NullName_ThrowsException() {
        // Assumption: Application validates input data before database operations
        assumeTrue(customerService != null, "Customer service must be available");

        // Arrange
        Customer invalidCustomer = new Customer(1002, null, "555-1002", "1002 Oak Ave");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(invalidCustomer),
                "Creating customer with null name should throw IllegalArgumentException"
        );

        assertEquals("Customer name cannot be null or empty", exception.getMessage());

        // Verify customer was not created in database
        Customer retrievedCustomer = customerService.getCustomerById(1002);
        assertNull(retrievedCustomer, "Customer with null name should not be created in database");
    }

    @Test
    @DisplayName("Test creating a customer with invalid data - empty name")
    void testCreateCustomer_EmptyName_ThrowsException() {
        // Arrange
        Customer invalidCustomer = new Customer(1003, "", "555-1003", "1003 Pine St");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(invalidCustomer)
        );

        assertEquals("Customer name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Test creating a customer with invalid data - null contact number")
    void testCreateCustomer_NullContactNumber_ThrowsException() {
        // Arrange
        Customer invalidCustomer = new Customer(1004, "Valid Name", null, "1004 Maple St");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(invalidCustomer)
        );

        assertEquals("Contact number cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Test creating a customer with null customer object")
    void testCreateCustomer_NullCustomer_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(null)
        );

        assertEquals("Customer cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test creating a customer with duplicate ID")
    void testCreateCustomer_DuplicateId_ThrowsException() {
        // Assumption: Pre-populated data exists (customer with ID 100)
        Customer existingCustomer = customerService.getCustomerById(100);
        assumeTrue(existingCustomer != null, "Pre-populated customer with ID 100 should exist");

        // Arrange - Try to create customer with same ID as pre-populated data
        Customer duplicateCustomer = new Customer(100, "Duplicate Customer", "555-DUPE", "Duplicate Address");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(duplicateCustomer)
        );

        assertTrue(exception.getMessage().contains("already exists"),
                "Exception message should indicate duplicate ID");
    }

    // ==================== READ CUSTOMER TESTS ====================

    @Test
    @DisplayName("Test retrieving a customer by ID")
    void testRetrieveCustomer_ValidId_Success() {
        // Assumption: Pre-populated data exists in test database
        assumeTrue(customerService != null, "Customer service must be available");

        // Act - Retrieve pre-populated customer
        Customer retrievedCustomer = customerService.getCustomerById(100);

        // Assert
        assertNotNull(retrievedCustomer, "Customer should be found in database");
        assertEquals(100, retrievedCustomer.getCustId(), "Customer ID should match");
        assertEquals("John Smith", retrievedCustomer.getCustomerName(), "Customer name should match pre-populated data");
        assertEquals("555-0001", retrievedCustomer.getContactNumber(), "Contact number should match pre-populated data");
        assertEquals("100 Main St", retrievedCustomer.getAddress(), "Address should match pre-populated data");
    }

    @Test
    @DisplayName("Test retrieving a customer by non-existent ID")
    void testRetrieveCustomer_NonExistentId_ReturnsNull() {
        // Act
        Customer retrievedCustomer = customerService.getCustomerById(9999);

        // Assert
        assertNull(retrievedCustomer, "Non-existent customer should return null");
    }

    @Test
    @DisplayName("Test retrieving a customer with invalid ID")
    void testRetrieveCustomer_InvalidId_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.getCustomerById(0)
        );

        assertEquals("Customer ID must be positive", exception.getMessage());

        // Test negative ID
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.getCustomerById(-1)
        );

        assertEquals("Customer ID must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Test retrieving all customers")
    void testRetrieveAllCustomers_Success() {
        // Assumption: Test database has pre-populated data
        assumeTrue(customerService != null, "Customer service must be available");

        // Act
        List<Customer> allCustomers = customerService.getAllCustomers();

        // Assert
        assertNotNull(allCustomers, "Customer list should not be null");
        assertEquals(3, allCustomers.size(), "Should have 3 pre-populated customers");

        // Verify pre-populated data exists
        assertTrue(allCustomers.stream().anyMatch(c -> c.getCustId() == 100), "Customer 100 should exist");
        assertTrue(allCustomers.stream().anyMatch(c -> c.getCustId() == 200), "Customer 200 should exist");
        assertTrue(allCustomers.stream().anyMatch(c -> c.getCustId() == 300), "Customer 300 should exist");
    }

    // ==================== UPDATE CUSTOMER TESTS ====================

    @Test
    @DisplayName("Test updating a customer's information")
    void testUpdateCustomer_ValidData_Success() {
        // Assumption: Pre-populated customer exists for updating
        Customer existingCustomer = customerService.getCustomerById(200);
        assumeTrue(existingCustomer != null, "Pre-populated customer with ID 200 should exist");

        // Arrange - Create updated customer with same ID but different data
        Customer updatedCustomer = new Customer(200, "Jane Smith Updated", "555-0002-NEW", "200 Oak Ave Updated");

        // Act
        assertDoesNotThrow(() -> customerService.updateCustomer(updatedCustomer));

        // Assert - Verify changes were saved to database
        Customer retrievedCustomer = customerService.getCustomerById(200);
        assertNotNull(retrievedCustomer, "Updated customer should exist in database");
        assertEquals(200, retrievedCustomer.getCustId(), "Customer ID should remain unchanged");
        assertEquals("Jane Smith Updated", retrievedCustomer.getCustomerName(), "Customer name should be updated");
        assertEquals("555-0002-NEW", retrievedCustomer.getContactNumber(), "Contact number should be updated");
        assertEquals("200 Oak Ave Updated", retrievedCustomer.getAddress(), "Address should be updated");
    }

    @Test
    @DisplayName("Test updating a non-existent customer")
    void testUpdateCustomer_NonExistentId_ThrowsException() {
        // Arrange
        Customer nonExistentCustomer = new Customer(9999, "Non Existent", "555-9999", "9999 Nowhere St");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.updateCustomer(nonExistentCustomer)
        );

        assertTrue(exception.getMessage().contains("does not exist"),
                "Exception should indicate customer does not exist");
    }

    @Test
    @DisplayName("Test updating a customer with invalid data")
    void testUpdateCustomer_InvalidData_ThrowsException() {
        // Test null customer
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.updateCustomer(null)
        );
        assertEquals("Customer cannot be null", exception.getMessage());

        // Test empty name
        Customer invalidCustomer = new Customer(200, "", "555-0002", "200 Oak Ave");
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.updateCustomer(invalidCustomer)
        );
        assertEquals("Customer name cannot be null or empty", exception.getMessage());

        // Test null contact number
        Customer invalidCustomer2 = new Customer(200, "Valid Name", null, "200 Oak Ave");
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.updateCustomer(invalidCustomer2)
        );
        assertEquals("Contact number cannot be null or empty", exception.getMessage());
    }

    // ==================== DELETE CUSTOMER TESTS ====================

    @Test
    @DisplayName("Test deleting a customer")
    void testDeleteCustomer_ValidId_Success() {
        // Assumption: Pre-populated customer exists for deletion
        Customer existingCustomer = customerService.getCustomerById(300);
        assumeTrue(existingCustomer != null, "Pre-populated customer with ID 300 should exist for deletion");

        // Act
        boolean deleteResult = customerService.deleteCustomer(300);

        // Assert
        assertTrue(deleteResult, "Delete operation should return true");

        // Verify customer was removed from database
        Customer deletedCustomer = customerService.getCustomerById(300);
        assertNull(deletedCustomer, "Deleted customer should not exist in database");

        // Verify other customers still exist
        assertNotNull(customerService.getCustomerById(100), "Other customers should remain in database");
        assertNotNull(customerService.getCustomerById(200), "Other customers should remain in database");
    }

    @Test
    @DisplayName("Test deleting a non-existent customer")
    void testDeleteCustomer_NonExistentId_ReturnsFalse() {
        // Act
        boolean deleteResult = customerService.deleteCustomer(9999);

        // Assert
        assertFalse(deleteResult, "Deleting non-existent customer should return false");
    }

    @Test
    @DisplayName("Test deleting a customer with invalid ID")
    void testDeleteCustomer_InvalidId_ThrowsException() {
        // Test zero ID
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.deleteCustomer(0)
        );
        assertEquals("Customer ID must be positive", exception.getMessage());

        // Test negative ID
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.deleteCustomer(-5)
        );
        assertEquals("Customer ID must be positive", exception.getMessage());
    }

    // ==================== INTEGRATION TESTS ====================

    @Test
    @DisplayName("Integration test - Create, Read, Update, Delete cycle")
    void testCRUDCycle_CompleteWorkflow_Success() {
        // Assumption: Database supports full CRUD operations
        assumeTrue(customerService != null, "Customer service must be available for CRUD operations");

        // CREATE - Add new customer
        Customer newCustomer = new Customer(2001, "Integration Test User", "555-INT", "Integration Address");
        assertDoesNotThrow(() -> customerService.addCustomer(newCustomer));

        // READ - Verify customer was created
        Customer createdCustomer = customerService.getCustomerById(2001);
        assertNotNull(createdCustomer, "Customer should be created");
        assertEquals("Integration Test User", createdCustomer.getCustomerName());

        // UPDATE - Modify customer data
        Customer updatedCustomer = new Customer(2001, "Updated Integration User", "555-UPD", "Updated Address");
        assertDoesNotThrow(() -> customerService.updateCustomer(updatedCustomer));

        // READ - Verify update was successful
        Customer modifiedCustomer = customerService.getCustomerById(2001);
        assertNotNull(modifiedCustomer, "Updated customer should exist");
        assertEquals("Updated Integration User", modifiedCustomer.getCustomerName(), "Name should be updated");
        assertEquals("555-UPD", modifiedCustomer.getContactNumber(), "Contact should be updated");

        // DELETE - Remove customer
        boolean deleteResult = customerService.deleteCustomer(2001);
        assertTrue(deleteResult, "Delete should be successful");

        // READ - Verify deletion was successful
        Customer deletedCustomer = customerService.getCustomerById(2001);
        assertNull(deletedCustomer, "Customer should be deleted");
    }

    @Test
    @DisplayName("Test database persistence across operations")
    void testDatabasePersistence_MultipleOperations_DataPersists() {
        // Assumption: Database persists data across multiple operations
        assumeTrue(customerService != null, "Customer service must be available");

        // Create multiple customers
        Customer customer1 = new Customer(3001, "Persistence Test 1", "555-P001", "Address 1");
        Customer customer2 = new Customer(3002, "Persistence Test 2", "555-P002", "Address 2");

        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);

        // Perform operations on one customer
        customerService.updateCustomer(new Customer(3001, "Updated Persistence Test 1", "555-P001-UPD", "Updated Address 1"));

        // Verify both customers persist and first customer was updated
        Customer retrieved1 = customerService.getCustomerById(3001);
        Customer retrieved2 = customerService.getCustomerById(3002);

        assertNotNull(retrieved1, "First customer should persist");
        assertNotNull(retrieved2, "Second customer should persist");
        assertEquals("Updated Persistence Test 1", retrieved1.getCustomerName(), "First customer should be updated");
        assertEquals("Persistence Test 2", retrieved2.getCustomerName(), "Second customer should remain unchanged");

        // Verify total count includes pre-populated data + new customers
        List<Customer> allCustomers = customerService.getAllCustomers();
        assertEquals(5, allCustomers.size(), "Should have 3 pre-populated + 2 new customers");
    }

    @Test
    @DisplayName("Test database assumptions validation")
    void testDatabaseAssumptions_ValidateTestEnvironment() {
        // Validate all assumptions about test environment

        // Assumption 1: Database connection is available
        assertDoesNotThrow(() -> {
            try (Connection conn = testDatabaseConnection.getConnection()) {
                assertNotNull(conn, "Database connection should be available");
                assertFalse(conn.isClosed(), "Database connection should be active");
            }
        }, "Database connection should be working");

        // Assumption 2: Test database has pre-populated data
        List<Customer> prePopulatedData = customerService.getAllCustomers();
        assertEquals(3, prePopulatedData.size(), "Test database should have 3 pre-populated customers");

        // Assumption 3: CRUD operations work with database
        assertDoesNotThrow(() -> {
            // Test basic CRUD operations don't throw database errors
            Customer testCust = new Customer(9000, "DB Test", "555-DB", "DB Address");
            customerService.addCustomer(testCust);
            customerService.getCustomerById(9000);
            customerService.updateCustomer(new Customer(9000, "DB Updated", "555-DB", "DB Address"));
            customerService.deleteCustomer(9000);
        }, "All CRUD operations should work with database");
    }
}
