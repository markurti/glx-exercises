import org.example.Entity.Reservation;
import org.example.Database.DatabaseConnectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    private Reservation reservation;

    // Common mocks used across tests
    private Connection mockConnection;
    private PreparedStatement insertStmt;
    private PreparedStatement selectHotelStmt;
    private PreparedStatement selectAvailableStmt;
    private PreparedStatement updateRoomFalseStmt;
    private PreparedStatement updateRoomTrueStmt;
    private PreparedStatement updateReservationStmt;
    private PreparedStatement selectRoomIdStmt;
    private PreparedStatement checkCancelledStmt;
    private PreparedStatement defaultStmt;

    private ResultSet rsHotelId;
    private ResultSet rsAvailable;
    private ResultSet rsRoomId;
    private ResultSet rsStatus;

    @BeforeEach
    void setUp() {
        reservation = new Reservation(1, 101, 201,
                Date.valueOf(LocalDate.now().plusDays(1)),
                Date.valueOf(LocalDate.now().plusDays(3)),
                "free", 301);

        mockConnection = mock(Connection.class);

        // Prepare statement mocks
        insertStmt = mock(PreparedStatement.class);
        selectHotelStmt = mock(PreparedStatement.class);
        selectAvailableStmt = mock(PreparedStatement.class);
        updateRoomFalseStmt = mock(PreparedStatement.class);
        updateRoomTrueStmt = mock(PreparedStatement.class);
        updateReservationStmt = mock(PreparedStatement.class);
        selectRoomIdStmt = mock(PreparedStatement.class);
        checkCancelledStmt = mock(PreparedStatement.class);
        defaultStmt = mock(PreparedStatement.class);

        // ResultSets
        rsHotelId = mock(ResultSet.class);
        rsAvailable = mock(ResultSet.class);
        rsRoomId = mock(ResultSet.class);
        rsStatus = mock(ResultSet.class);
    }

    /**
     * Helper: wire mockConnection.prepareStatement(sql) -> appropriate PreparedStatement
     * using contains checks so minor whitespace/casts don't break mocking.
     */
    private void wirePreparedStatementRouting() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenAnswer(invocation -> {
            String sql = invocation.getArgument(0, String.class);
            if (sql.contains("INSERT INTO Reservation")) return insertStmt;
            if (sql.contains("SELECT hotel_id FROM Room")) return selectHotelStmt;
            if (sql.contains("SELECT available FROM Room")) return selectAvailableStmt;
            if (sql.contains("UPDATE Room SET available = FALSE")) return updateRoomFalseStmt;
            if (sql.contains("UPDATE Room SET available = TRUE")) return updateRoomTrueStmt;
            if (sql.contains("UPDATE Reservation SET status")) return updateReservationStmt;
            if (sql.contains("SELECT room_id FROM Reservation")) return selectRoomIdStmt;
            if (sql.contains("SELECT status FROM Reservation")) return checkCancelledStmt;
            return defaultStmt;
        });
    }

    @Test
    void testMakeReservation_Success() throws Exception {
        try (MockedStatic<DatabaseConnectionManager> mockedDb = mockStatic(DatabaseConnectionManager.class)) {
            mockedDb.when(DatabaseConnectionManager::getConnection).thenReturn(mockConnection);

            // route SQL -> preparedStatements
            wirePreparedStatementRouting();

            // Insert: simulate success
            when(insertStmt.executeUpdate()).thenReturn(1);

            // isRoomInHotel: return hotel_id matching reservation.getHotel_id()
            when(selectHotelStmt.executeQuery()).thenReturn(rsHotelId);
            when(rsHotelId.next()).thenReturn(true);
            when(rsHotelId.getInt("hotel_id")).thenReturn(301);

            // isRoomAvailable: available = true
            when(selectAvailableStmt.executeQuery()).thenReturn(rsAvailable);
            when(rsAvailable.next()).thenReturn(true);
            when(rsAvailable.getBoolean("available")).thenReturn(true);

            // changeRoomStatusToFalse: simulate update success
            when(updateRoomFalseStmt.executeUpdate()).thenReturn(1);

            // Execute
            reservation.makeReservation(reservation);

            // Verifications (basic)
            verify(insertStmt, times(1)).executeUpdate();
            verify(updateRoomFalseStmt, times(1)).executeUpdate(); // ensure room status update was called
        }
    }

    @Test
    void testMakeReservation_Failure_NoRowsAffected() throws Exception {
        try (MockedStatic<DatabaseConnectionManager> mockedDb = mockStatic(DatabaseConnectionManager.class)) {
            mockedDb.when(DatabaseConnectionManager::getConnection).thenReturn(mockConnection);

            wirePreparedStatementRouting();

            // Insert returns 0 -> failure path, changeRoomStatusToFalse should NOT be called
            when(insertStmt.executeUpdate()).thenReturn(0);

            // isRoomInHotel: still needs to be mocked (checked before insert)
            when(selectHotelStmt.executeQuery()).thenReturn(rsHotelId);
            when(rsHotelId.next()).thenReturn(true);
            when(rsHotelId.getInt("hotel_id")).thenReturn(301);

            // isRoomAvailable
            when(selectAvailableStmt.executeQuery()).thenReturn(rsAvailable);
            when(rsAvailable.next()).thenReturn(true);
            when(rsAvailable.getBoolean("available")).thenReturn(true);

            // Guard: if changeRoomStatusToFalse were called for some reason, make it safe
            when(updateRoomFalseStmt.executeUpdate()).thenReturn(1);

            // Execute
            reservation.makeReservation(reservation);

            // Verify insert attempted
            verify(insertStmt, times(1)).executeUpdate();
            // Should not attempt to change room status because insert returned 0
            verify(updateRoomFalseStmt, times(0)).executeUpdate();
        }
    }

    @Test
    void testCancelRoomReservation_Success() throws Exception {
        try (MockedStatic<DatabaseConnectionManager> mockedDb = mockStatic(DatabaseConnectionManager.class)) {
            mockedDb.when(DatabaseConnectionManager::getConnection).thenReturn(mockConnection);

            wirePreparedStatementRouting();

            // reservationAlreadyCancelled: return a non-cancelled status (e.g., "confirmed")
            when(checkCancelledStmt.executeQuery()).thenReturn(rsStatus);
            when(rsStatus.next()).thenReturn(true);
            when(rsStatus.getString("status")).thenReturn("confirmed");

            // Update reservation (set status = 'cancelled') -> success
            when(updateReservationStmt.executeUpdate()).thenReturn(1);

            // Select room_id for this reservation
            when(selectRoomIdStmt.executeQuery()).thenReturn(rsRoomId);
            when(rsRoomId.next()).thenReturn(true);
            when(rsRoomId.getInt("room_id")).thenReturn(201);

            // changeRoomStatusToTrue should update room availability -> simulate success
            when(updateRoomTrueStmt.executeUpdate()).thenReturn(1);

            // Execute
            boolean result = reservation.cancelRoomReservation(1);

            assertTrue(result);

            verify(updateReservationStmt, times(1)).executeUpdate();
            verify(selectRoomIdStmt, times(1)).executeQuery();
            verify(updateRoomTrueStmt, times(1)).executeUpdate();
        }
    }

    @Test
    void testCancelRoomReservation_FailsWhenNoReservationFound() throws Exception {
        try (MockedStatic<DatabaseConnectionManager> mockedDb = mockStatic(DatabaseConnectionManager.class)) {
            mockedDb.when(DatabaseConnectionManager::getConnection).thenReturn(mockConnection);

            wirePreparedStatementRouting();

            // reservationAlreadyCancelled: simulate no row found
            when(checkCancelledStmt.executeQuery()).thenReturn(rsStatus);
            when(rsStatus.next()).thenReturn(false);

            // UPDATE reservation returns 0 (no rows affected)
            when(updateReservationStmt.executeUpdate()).thenReturn(0);

            // Execute
            boolean result = reservation.cancelRoomReservation(99);

            assertFalse(result);

            verify(updateReservationStmt, times(1)).executeUpdate();
            // No changeRoomStatusToTrue should be called
            verify(updateRoomTrueStmt, times(0)).executeUpdate();
        }
    }
}
