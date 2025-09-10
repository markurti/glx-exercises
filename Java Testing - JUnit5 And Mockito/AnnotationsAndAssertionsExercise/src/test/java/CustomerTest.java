import org.example.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    @Timeout(value = 5)
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

        try {
            // Act - Attempt to create customer with null name
            customerService.addCustomer(invalidCustomer);

            // If no exception is thrown, the test should fail
            fail("Expected IllegalArgumentException was not thrown for null customer name");
        } catch (IllegalArgumentException e) {
            // Expected exception - verify error handling
            assertNotNull(e.getMessage(), "Exception message should not be null");
            assertEquals("Customer name cannot be null or empty", e.getMessage(),
                    "Exception message should be specific and informative");

            // Verify customer was not created in database
            Customer retrievedCustomer = customerService.getCustomerById(1002);
            assertNull(retrievedCustomer, "Customer with null name should not be created in database");

            // Verify system state remains consistent
            List<Customer> customers = customerService.getAllCustomers();
            assertEquals(3, customers.size(), "Customer count should remain unchanged after validation failure");
        } catch (Exception e) {
            // Unexpected exception type
            fail(String.format("Expected IllegalArgumentException but got %s: %s", e.getClass().getSimpleName(), e.getMessage()));
        }
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
        String originalCustomerName = existingCustomer.getCustomerName();

        try {
            // Act
            customerService.addCustomer(duplicateCustomer);
            fail("Expected exception for duplicate customer ID");

        } catch (IllegalArgumentException e) {
            // Expected exception - verify proper handling
            assertTrue(e.getMessage().contains("already exists"),
                    "Exception message should indicate duplicate ID");
            assertTrue(e.getMessage().contains("100"),
                    "Exception message should include the conflicting ID");

            // Verify original customer data is preserved
            Customer preservedCustomer = customerService.getCustomerById(100);
            assertNotNull(preservedCustomer, "Original customer should still exist");
            assertEquals(originalCustomerName, preservedCustomer.getCustomerName(),
                    "Original customer data should be preserved");

            System.out.println("✓ Duplicate ID properly handled - original data preserved");

        } catch (RuntimeException e) {
            // Handle potential database constraint violations
            if (e.getMessage().contains("duplicate") || e.getMessage().contains("unique")) {
                System.out.println("Database constraint caught duplicate: " + e.getMessage());

                // Verify data integrity
                Customer preservedCustomer = customerService.getCustomerById(100);
                assertEquals(originalCustomerName, preservedCustomer.getCustomerName(),
                        "Database should maintain original customer data");
            } else {
                fail("Unexpected RuntimeException: " + e.getMessage());
            }

        } catch (Exception e) {
            fail("Unexpected exception type: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    // ==================== READ CUSTOMER TESTS ====================

    @Test
    @DisplayName("Test retrieving a customer by ID")
    @Timeout(value = 3)
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
    @Timeout(value = 5)
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
    @Timeout(value = 10)
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

        try {
            // Act
            customerService.updateCustomer(nonExistentCustomer);
            fail("Expected exception when updating non-existent customer");

        } catch (IllegalArgumentException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("does not exist"),
                    "Exception should indicate customer does not exist");
            assertTrue(e.getMessage().contains("9999"),
                    "Exception should include the non-existent customer ID");

            // Verify no customer was created as side effect
            Customer shouldNotExist = customerService.getCustomerById(9999);
            assertNull(shouldNotExist, "Failed update should not create new customer");

            // Verify existing data is intact
            assertEquals(3, customerService.getAllCustomers().size(),
                    "Existing customer count should remain unchanged");

            System.out.println("✓ Non-existent customer update properly rejected");

        } catch (RuntimeException e) {
            // Handle potential database-level errors
            if (e.getMessage().contains("not found") || e.getMessage().contains("does not exist")) {
                System.out.println("Database-level validation caught non-existent customer");
                assertNull(customerService.getCustomerById(9999), "Customer should still not exist");
            } else {
                fail("Unexpected RuntimeException: " + e.getMessage());
            }

        } catch (Exception e) {
            fail("Unexpected exception type: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Test updating a customer with invalid data")
    void testUpdateCustomer_InvalidData_ThrowsException() {
        // Test null customer using assertThrows (simple case)
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.updateCustomer(null)
        );
        assertEquals("Customer cannot be null", exception.getMessage());

        // Test empty name using try-catch for detailed validation
        Customer invalidCustomer = new Customer(200, "", "555-0002", "200 Oak Ave");

        try {
            customerService.updateCustomer(invalidCustomer);
            fail("Expected exception for empty customer name");

        } catch (IllegalArgumentException e) {
            assertEquals("Customer name cannot be null or empty", e.getMessage());

            // Verify original customer data is preserved
            Customer originalCustomer = customerService.getCustomerById(200);
            assertNotNull(originalCustomer, "Original customer should still exist");
            assertEquals("Jane Doe", originalCustomer.getCustomerName(),
                    "Original customer name should be preserved");

            System.out.println("✓ Empty name update rejected - original data preserved");

        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // Test null contact number using assertThrows (simple case)
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
    @Timeout(value = 5)
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
        try {
            // Act
            boolean deleteResult = customerService.deleteCustomer(9999);

            // Assert - Should return false, not throw exception
            assertFalse(deleteResult, "Deleting non-existent customer should return false");

            // Verify system stability
            assertEquals(3, customerService.getAllCustomers().size(),
                    "Customer count should remain unchanged");

            System.out.println("✓ Non-existent customer deletion handled gracefully");

        } catch (IllegalArgumentException e) {
            // Some implementations might throw exception for invalid operations
            if (e.getMessage().contains("positive") || e.getMessage().contains("valid")) {
                System.out.println("Implementation validates ID before deletion: " + e.getMessage());
            } else {
                fail("Unexpected validation error: " + e.getMessage());
            }

        } catch (Exception e) {
            fail("Unexpected exception during non-existent customer deletion: " + e.getMessage());
        }
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

    @ParameterizedTest
    @CsvSource({
            "3001, 'Integration Customer A', '555-INT-A', 'Integration Address A'",
            "3002, 'Integration Customer B', '555-INT-B', 'Integration Address B'",
            "3003, 'Integration Customer C', '555-INT-C', 'Integration Address C'"
    })
    @DisplayName("Integration test - Create, Read, Update, Delete cycle with multiple customers")
    @Timeout(value = 15)
    void testCRUDCycle_CompleteWorkflow_Success(int custId, String originalName, String originalContact, String originalAddress) {
        // Assumption: Database supports full CRUD operations
        assumeTrue(customerService != null, "Customer service must be available for CRUD operations");

        // CREATE - Add new customer
        Customer newCustomer = new Customer(custId, originalName, originalContact, originalAddress);
        assertDoesNotThrow(() -> customerService.addCustomer(newCustomer));

        // READ - Verify customer was created
        Customer createdCustomer = customerService.getCustomerById(custId);
        assertNotNull(createdCustomer, String.format("Customer %d should be created", custId));
        assertEquals(originalName, createdCustomer.getCustomerName());

        // UPDATE - Modify customer data
        String updatedName = "Updated " + originalName;
        String updatedContact = originalContact.replace("INT", "UPD");
        String updatedAddress = "Updated " + originalAddress;
        Customer updatedCustomer = new Customer(custId, updatedName, updatedContact, updatedAddress);
        assertDoesNotThrow(() -> customerService.updateCustomer(updatedCustomer));

        // READ - Verify update was successful
        Customer modifiedCustomer = customerService.getCustomerById(custId);
        assertNotNull(modifiedCustomer, String.format("Updated customer %d should exist", custId));
        assertEquals(updatedName, modifiedCustomer.getCustomerName(), "Name should be updated");
        assertEquals(updatedContact, modifiedCustomer.getContactNumber(), "Contact should be updated");

        // DELETE - Remove customer
        boolean deleteResult = customerService.deleteCustomer(custId);
        assertTrue(deleteResult, String.format("Delete of customer %d should be successful", custId));

        // READ - Verify deletion was successful
        Customer deletedCustomer = customerService.getCustomerById(custId);
        assertNull(deletedCustomer, String.format("Customer %d should be deleted", custId));

        System.out.printf("✓ CRUD cycle completed successfully for customer %d (%s)%n", custId, originalName);
    }

    @Test
    @DisplayName("Test database persistence across operations")
    @Timeout(value = 10)
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
    @Timeout(value = 8)
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
