import org.example.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @CsvSource({
            "2001, 'Alice Johnson', '555-2001', '2001 Elm St'",
            "2002, 'Bob Williams', '555-2002', '2002 Oak Ave'",
            "2003, 'Carol Brown', '555-2003', '2003 Pine St'",
            "2004, 'David Davis', '555-2004', '2004 Maple Ave'",
            "2005, 'Eva Garcia', '555-2005', '2005 Cedar Blvd'"
    })
    @DisplayName("Test creation of multiple valid customers with different data")
    @Timeout(value = 10) // Multiple database operations
    void testCreateCustomer_ValidData_Success(int custId, String name, String contact, String address) {
        // Arrange
        Customer newCustomer = new Customer(custId, name, contact, address);

        // Act
        assertDoesNotThrow(() -> customerService.addCustomer(newCustomer),
                String.format("Creating valid customer %d should not throw exception", custId));

        // Assert - Verify customer was created correctly
        Customer retrievedCustomer = customerService.getCustomerById(custId);
        assertNotNull(retrievedCustomer,
                String.format("Customer %d should be created in database", custId));
        assertEquals(custId, retrievedCustomer.getCustId(),
                "Customer ID should match");
        assertEquals(name, retrievedCustomer.getCustomerName(),
                String.format("Customer name should match for ID %d", custId));
        assertEquals(contact, retrievedCustomer.getContactNumber(),
                String.format("Contact number should match for ID %d", custId));
        assertEquals(address, retrievedCustomer.getAddress(),
                String.format("Address should match for ID %d", custId));

        System.out.printf("✓ Customer %d (%s) created successfully%n", custId, name);
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

    @ParameterizedTest
    @CsvSource({
            "1001, '', '555-1001', '1001 Elm St', 'Customer name cannot be null or empty'",
            "1002, '  ', '555-1002', '1002 Oak Ave', 'Customer name cannot be null or empty'",
            "1003, 'Valid Name', '', '1003 Pine St', 'Contact number cannot be null or empty'",
            "1004, 'Valid Name', '   ', '1004 Maple St', 'Contact number cannot be null or empty'"
    })
    @DisplayName("Test customer creation with various invalid data combinations")
    void testCreateCustomer_InvalidData_Validation(int custId, String name, String contact, String address, String expectedMessage) {
        // Handle empty strings that should be treated as empty (not null)
        String customerName = name.trim().isEmpty() ? "" : name;
        String contactNumber = contact.trim().isEmpty() ? "" : contact;

        // Arrange
        Customer invalidCustomer = new Customer(custId, customerName, contactNumber, address);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(invalidCustomer),
                String.format("Customer with invalid data should throw exception: ID=%d, Name='%s', Contact='%s'",
                        custId, customerName, contactNumber)
        );

        assertEquals(expectedMessage, exception.getMessage(),
                String.format("Exception message should match expected for customer ID %d", custId));

        // Verify customer was not created
        Customer retrievedCustomer = customerService.getCustomerById(custId);
        assertNull(retrievedCustomer,
                String.format("Invalid customer with ID %d should not be created", custId));

        // Verify system integrity
        assertEquals(3, customerService.getAllCustomers().size(),
                "Customer count should remain unchanged after validation failure");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/customer-boundary-test-data.csv", numLinesToSkip = 1)
    @DisplayName("Test customer boundary conditions using CSV file")
    @Timeout(value = 15)
    void testCustomerBoundaryConditions(int custId, String customerName, String contactNumber, String address, String expectedResult) {
        // Arrange
        Customer customer = new Customer(custId, customerName, contactNumber, address);

        if ("valid".equalsIgnoreCase(expectedResult)) {
            // Should succeed
            assertDoesNotThrow(() -> customerService.addCustomer(customer),
                    String.format("Valid boundary customer %d should be created", custId));

            // Verify data integrity
            Customer retrieved = customerService.getCustomerById(custId);
            assertNotNull(retrieved, String.format("Customer %d should be retrievable", custId));
            assertEquals(customerName, retrieved.getCustomerName(), "Name should be preserved exactly");
            assertEquals(contactNumber, retrieved.getContactNumber(), "Contact should be preserved exactly");
            assertEquals(address, retrieved.getAddress(), "Address should be preserved exactly");

            System.out.printf("✓ Boundary customer %d created successfully: %s%n", custId, customerName);

        } else if ("invalid".equalsIgnoreCase(expectedResult)) {
            // Should fail with validation exception
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> customerService.addCustomer(customer),
                    String.format("Invalid boundary customer %d should throw exception", custId)
            );

            // Verify appropriate error message
            assertTrue(exception.getMessage().contains("cannot be null or empty"),
                    "Exception should indicate validation failure");

            // Verify customer was not created
            Customer shouldNotExist = customerService.getCustomerById(custId);
            assertNull(shouldNotExist, String.format("Invalid customer %d should not be created", custId));

            System.out.printf("✓ Invalid boundary customer %d properly rejected: %s%n", custId, exception.getMessage());

        } else {
            fail("Unknown expected result in CSV: " + expectedResult);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'A', '1', 'X'",  // Minimal valid data
            "12345, 'Customer With Numbers 123', '555-ALPHA-1234', '123 Numeric Street Apt 456'",  // Mixed alphanumeric
            "7777, 'Special-Customer_Name', '555.123.4567', '123 Main St., Suite #500'"  // Special characters
    })
    @DisplayName("Test customer creation with boundary and edge case values")
    @Timeout(value = 8)
    void testCreateCustomer_BoundaryValues_Success(int custId, String name, String contact, String address) {
        // Arrange
        Customer boundaryCustomer = new Customer(custId, name, contact, address);

        // Act & Assert
        assertDoesNotThrow(() -> customerService.addCustomer(boundaryCustomer),
                String.format("Creating customer with boundary values should succeed: ID=%d", custId));

        // Verify data integrity
        Customer retrieved = customerService.getCustomerById(custId);
        assertNotNull(retrieved, String.format("Boundary customer %d should be retrievable", custId));
        assertEquals(name, retrieved.getCustomerName(), "Name should be preserved exactly");
        assertEquals(contact, retrieved.getContactNumber(), "Contact should be preserved exactly");
        assertEquals(address, retrieved.getAddress(), "Address should be preserved exactly");

        System.out.printf("✓ Boundary customer %d created and verified successfully%n", custId);
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

    @ParameterizedTest
    @ValueSource(ints = {9999, 8888, 7777, 5000, 1500})
    @DisplayName("Test retrieval of non-existent customers with various IDs")
    void testRetrieveCustomer_NonExistentIds_ReturnsNull(int nonExistentId) {
        // Act
        Customer retrievedCustomer = customerService.getCustomerById(nonExistentId);

        // Assert
        assertNull(retrievedCustomer,
                String.format("Customer with non-existent ID %d should return null", nonExistentId));

        // Verify system stability
        assertEquals(3, customerService.getAllCustomers().size(),
                "Customer count should remain unchanged after non-existent customer lookup");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -5, -100, -999})
    @DisplayName("Test retrieving a customer with invalid ID")
    void testRetrieveCustomer_InvalidIds_ThrowsException(int invalidId) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.getCustomerById(invalidId),
                String.format("Customer ID %d should throw IllegalArgumentException", invalidId)
        );

        assertEquals("Customer ID must be positive", exception.getMessage(),
                String.format("Exception message should be consistent for invalid ID: %d", invalidId));

        // Verify system remains stable after each invalid attempt
        assertEquals(3, customerService.getAllCustomers().size(),
                "Customer count should remain unchanged after invalid ID attempt");
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

    @ParameterizedTest
    @ValueSource(ints = {9999, 8888, 7777, 6666, 5555})
    @DisplayName("Test deletion of non-existent customers returns false gracefully")
    void testDeleteCustomer_NonExistentIds_ReturnsFalse(int nonExistentId) {
        try {
            // Act
            boolean deleteResult = customerService.deleteCustomer(nonExistentId);

            // Assert - Should return false for non-existent customers
            assertFalse(deleteResult,
                    String.format("Deleting non-existent customer %d should return false", nonExistentId));

            // Verify system stability
            assertEquals(3, customerService.getAllCustomers().size(),
                    String.format("Customer count should remain unchanged after attempting to delete non-existent customer %d", nonExistentId));

            // Verify all pre-populated customers still exist
            assertNotNull(customerService.getCustomerById(100), "Customer 100 should still exist");
            assertNotNull(customerService.getCustomerById(200), "Customer 200 should still exist");
            assertNotNull(customerService.getCustomerById(300), "Customer 300 should still exist");

            System.out.printf("✓ Non-existent customer %d deletion handled gracefully%n", nonExistentId);

        } catch (IllegalArgumentException e) {
            // Some implementations might validate the ID first
            if (e.getMessage().contains("positive") || e.getMessage().contains("valid")) {
                System.out.printf("Implementation validates ID %d before deletion: %s%n", nonExistentId, e.getMessage());
            } else {
                fail(String.format("Unexpected validation error for ID %d: %s", nonExistentId, e.getMessage()));
            }
        } catch (Exception e) {
            fail(String.format("Unexpected exception for non-existent customer %d: %s", nonExistentId, e.getMessage()));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10, -50, -999})
    @DisplayName("Test deleting a customer with invalid ID")
    void testDeleteCustomer_InvalidIds_ThrowsException(int invalidId) {
        // Act & Assert - Test deletion with each invalid ID
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.deleteCustomer(invalidId),
                String.format("Deleting customer with invalid ID %d should throw exception", invalidId)
        );

        assertEquals("Customer ID must be positive", exception.getMessage(),
                String.format("Exception message should be consistent for invalid ID: %d", invalidId));

        // Verify no customers were accidentally deleted
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(3, customers.size(),
                String.format("No customers should be deleted for invalid ID: %d", invalidId));

        // Verify pre-populated customers still exist
        assertNotNull(customerService.getCustomerById(100), "Customer 100 should still exist");
        assertNotNull(customerService.getCustomerById(200), "Customer 200 should still exist");
        assertNotNull(customerService.getCustomerById(300), "Customer 300 should still exist");
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

    @TestFactory
    @DisplayName("Dynamic tests to ensure customer name is not empty")
    Stream<DynamicTest> dynamicTestCustomerNameIsNotEmpty() {
        String[] invalidNames = {"", "   ", "\t", "\n", "    \t  \n  "};

        return Arrays.stream(invalidNames)
                .map(invalidName -> DynamicTest.dynamicTest(
                   String.format("Test invalid customer name: '%s'", invalidName),
                        () -> dynamicTestForCustomer(invalidName)
                ));
    }

    private void dynamicTestForCustomer(String customerName) {
        int custId = 8000 + Math.abs((customerName != null ? customerName : "null").hashCode());
        Customer testCustomer = new Customer(custId, customerName, "555-8000", "TestAddress");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.addCustomer(testCustomer),
                String.format("Customer with invalid name '%s' should throw exception", customerName)
        );

        assertTrue(exception.getMessage().contains("Customer name cannot be null or empty"),
                "Exception should indicate name validation failure");

        // Verify customer was not created
        Customer shouldNotExist = customerService.getCustomerById(custId);
        assertNull(shouldNotExist, "Invalid customer should not be created");
    }
}
