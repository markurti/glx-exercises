import org.example.Customer;
import org.example.CustomerRepository;
import org.example.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service Unit Tests with Mockito")
public class CustomerServiceTest {
    @Mock
    private CustomerRepository mockCustomerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        // Create test customer data
        testCustomer = new Customer(1, "John Doe", "555-1234", "123 Main St");
    }

    @Test
    @DisplayName("Test adding a new customer successfully")
    void testAddNewCustomer_ValidData_Success() {
        // Arrange - Create a new customer object with valid data
        Customer newCustomer = new Customer(2, "Jane Smith", "888-5432", "542 Oak Ave");

        // Mock the repository to do nothing when addCustomer is called (void method)
        doNothing().when(mockCustomerRepository).addCustomer(newCustomer);

        // Mock the repository to return the customer when getCustomerById is called
        when(mockCustomerRepository.getCustomerById(2)).thenReturn(newCustomer);

        // Act - Call the addCustomer method
        assertDoesNotThrow(() -> customerService.addCustomer(newCustomer));

        // Verify that the customer is added to the system
        Customer retrievedCustomer = customerService.getCustomerById(2);

        // Assert - Verify subsequent calls to getCustomerById returns added customer
        assertNotNull(retrievedCustomer, "Retrieved customer should not be null");
        assertEquals(2, retrievedCustomer.getCustId(), "Customer ID should match");
        assertEquals("Jane Smith", retrievedCustomer.getCustomerName(), "Customer name should match");
        assertEquals("888-5432", retrievedCustomer.getContactNumber(), "Contact number should match");
        assertEquals("542 Oak Ave", retrievedCustomer.getAddress(), "Address should match");

        // Verify interactions with mock
        verify(mockCustomerRepository, times(1)).addCustomer(newCustomer);
        verify(mockCustomerRepository, times(1)).getCustomerById(2);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test adding customer with repository exception handling")
    void testAddNewCustomer_RepositoryThrowsException_PropagatesException() {
        // Arrange
        Customer newCustomer = new Customer(4, "Bob Wilson", "555-0000", "789 Pine St");

        // Mock repository to throw exception
        doThrow(new RuntimeException("Database connection failed"))
                .when(mockCustomerRepository).addCustomer(newCustomer);

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.addCustomer(newCustomer)
        );

        assertEquals("Database connection failed", exception.getMessage());

        // Verify repository interaction
        verify(mockCustomerRepository, times(1)).addCustomer(newCustomer);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test retrieving existing customer by valid ID")
    void testGetCustomerById_ValidId_ReturnsCorrectCustomer() {
        // Arrange - Provide a valid customer ID
        int validCustomerId = 1;

        // Mock the repository to return the expected customer
        when(mockCustomerRepository.getCustomerById(validCustomerId)).thenReturn(testCustomer);

        // Act - Call the getCustomerById method
        Customer retrievedCustomer = customerService.getCustomerById(validCustomerId);

        // Assert - Verify that the returned customer matches the expected customer
        assertNotNull(retrievedCustomer, "Retrieved customer should not be null");
        assertEquals(testCustomer.getCustId(), retrievedCustomer.getCustId(), "Customer ID should match");
        assertEquals(testCustomer.getCustomerName(), retrievedCustomer.getCustomerName(), "Customer name should match");
        assertEquals(testCustomer.getContactNumber(), retrievedCustomer.getContactNumber(), "Contact number should match");
        assertEquals(testCustomer.getAddress(), retrievedCustomer.getAddress(), "Address should match");

        // Verify interaction with mock
        verify(mockCustomerRepository, times(1)).getCustomerById(validCustomerId);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test retrieving customer with non-existent ID returns null")
    void testGetCustomerById_NonExistentId_ReturnsNull() {
        // Arrange
        int nonExistentId = 999;

        // Mock repository to return null for non-existent customer
        when(mockCustomerRepository.getCustomerById(nonExistentId)).thenReturn(null);

        // Act
        Customer retrievedCustomer = customerService.getCustomerById(nonExistentId);

        // Assert
        assertNull(retrievedCustomer, "Non-existent customer should return null");

        // Verify interaction
        verify(mockCustomerRepository, times(1)).getCustomerById(nonExistentId);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test retrieving multiple customers with different IDs")
    void testGetCustomerById_MultipleValidIds_ReturnsCorrectCustomers() {
        // Arrange
        Customer customer1 = new Customer(1, "John Doe", "555-1111", "111 First St");
        Customer customer2 = new Customer(2, "Jane Smith", "555-2222", "222 Second St");

        // Mock different returns for different IDs
        when(mockCustomerRepository.getCustomerById(1)).thenReturn(customer1);
        when(mockCustomerRepository.getCustomerById(2)).thenReturn(customer2);

        // Act
        Customer retrieved1 = customerService.getCustomerById(1);
        Customer retrieved2 = customerService.getCustomerById(2);

        // Assert
        assertEquals("John Doe", retrieved1.getCustomerName());
        assertEquals("Jane Smith", retrieved2.getCustomerName());

        // Verify interactions
        verify(mockCustomerRepository, times(1)).getCustomerById(1);
        verify(mockCustomerRepository, times(1)).getCustomerById(2);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test database failure during customer retrieval - Runtime Exception")
    void testGetCustomerById_DatabaseFailure_HandlesExceptionGracefully() {
        // Arrange
        int customerId = 1;

        // Mock the database dependency to throw an exception (simulate database failure)
        when(mockCustomerRepository.getCustomerById(customerId))
                .thenThrow(new RuntimeException("Database connection lost"));

        // Act & Assert - Call method that interacts with database
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.getCustomerById(customerId),
                "Expected RuntimeException due to database failure"
        );

        // Verify that the module handles the exception gracefully
        assertEquals("Database connection lost", exception.getMessage());

        // Verify interaction with mock
        verify(mockCustomerRepository, times(1)).getCustomerById(customerId);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test database failure during customer addition - SQL Exception wrapped")
    void testAddCustomer_DatabaseFailure_HandlesExceptionGracefully() {
        // Arrange
        Customer newCustomer = new Customer(5, "Test Customer", "555-TEST", "Test Address");

        // Mock database dependency to throw SQLException (wrapped in RuntimeException)
        doThrow(new RuntimeException("Database error while adding customer",
                new SQLException("Connection timeout")))
                .when(mockCustomerRepository).addCustomer(newCustomer);

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.addCustomer(newCustomer)
        );

        // Verify exception details
        assertTrue(exception.getMessage().contains("Database error while adding customer"));
        assertInstanceOf(SQLException.class, exception.getCause());

        // Verify interaction
        verify(mockCustomerRepository, times(1)).addCustomer(newCustomer);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test database failure during customer update")
    void testUpdateCustomer_DatabaseFailure_HandlesExceptionGracefully() {
        // Arrange
        Customer customerToUpdate = new Customer(1, "Updated Name", "555-UPDATED", "Updated Address");

        // Mock repository to throw exception during update
        doThrow(new RuntimeException("Database transaction failed"))
                .when(mockCustomerRepository).updateCustomer(customerToUpdate);

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.updateCustomer(customerToUpdate)
        );

        assertEquals("Database transaction failed", exception.getMessage());

        // Verify interaction
        verify(mockCustomerRepository, times(1)).updateCustomer(customerToUpdate);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test database failure during customer deletion")
    void testDeleteCustomer_DatabaseFailure_HandlesExceptionGracefully() {
        // Arrange
        int customerIdToDelete = 1;

        // Mock repository to throw exception during deletion
        when(mockCustomerRepository.deleteCustomer(customerIdToDelete))
                .thenThrow(new RuntimeException("Database locked - cannot delete"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.deleteCustomer(customerIdToDelete)
        );

        assertEquals("Database locked - cannot delete", exception.getMessage());

        // Verify interaction
        verify(mockCustomerRepository, times(1)).deleteCustomer(customerIdToDelete);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test successful customer update")
    void testUpdateCustomer_ValidData_Success() {
        // Arrange
        Customer updatedCustomer = new Customer(1, "John Doe Updated", "555-NEW", "New Address");

        // Mock repository methods
        doNothing().when(mockCustomerRepository).updateCustomer(updatedCustomer);

        // Act
        assertDoesNotThrow(() -> customerService.updateCustomer(updatedCustomer));

        // Verify interaction
        verify(mockCustomerRepository, times(1)).updateCustomer(updatedCustomer);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test successful customer deletion")
    void testDeleteCustomer_ValidId_Success() {
        // Arrange
        int customerIdToDelete = 1;

        // Mock repository to return true (successful deletion)
        when(mockCustomerRepository.deleteCustomer(customerIdToDelete)).thenReturn(true);

        // Act
        boolean result = customerService.deleteCustomer(customerIdToDelete);

        // Assert
        assertTrue(result, "Customer deletion should return true");

        // Verify interaction
        verify(mockCustomerRepository, times(1)).deleteCustomer(customerIdToDelete);
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test argument matching with Mockito matchers")
    void testArgumentMatching_WithMockitoMatchers() {
        // Arrange - Use argument matchers
        when(mockCustomerRepository.getCustomerById(anyInt())).thenReturn(testCustomer);

        // Act
        Customer result1 = customerService.getCustomerById(1);
        Customer result2 = customerService.getCustomerById(100);

        // Assert
        assertEquals(testCustomer, result1);
        assertEquals(testCustomer, result2);

        // Verify with argument matchers
        verify(mockCustomerRepository, times(2)).getCustomerById(anyInt());
        verify(mockCustomerRepository, never()).getCustomerById(eq(-1));
        verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    @DisplayName("Test argument capturing with Mockito")
    void testArgumentCapturing_VerifyExactArguments() {
        // Arrange
        Customer newCustomer = new Customer(10, "Captured Customer", "555-CAPTURE", "Capture Address");

        // Act
        customerService.addCustomer(newCustomer);

        // Verify with argument captor
        verify(mockCustomerRepository).addCustomer(argThat(customer ->
                customer.getCustId() == 10 &&
                        customer.getCustomerName().equals("Captured Customer")
        ));
    }
}
