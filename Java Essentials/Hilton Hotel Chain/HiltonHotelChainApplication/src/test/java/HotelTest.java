import org.example.Entity.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotelTest {
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        // Initialize hotel with details
        hotel = new Hotel(1, "Hilton Downtown", "New York", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void testAddHotelDetails() {
        // Verify initial hotel details
        assertEquals("Grand Palace", hotel.getName(), "Initial hotel name should be 'Grand Palace'");
        assertEquals("New York", hotel.getLocation(), "Initial hotel location should be 'New York'");

        // Update hotel details using setter methods
        hotel.setName("Ocean View Resort");
        hotel.setLocation("Miami");

        // Assert that updated hotel details match expected values
        assertEquals("Ocean View Resort", hotel.getName(), "Updated hotel name should be 'Ocean View Resort'");
        assertEquals("Miami", hotel.getLocation(), "Updated hotel location should be 'Miami'");
    }
}
