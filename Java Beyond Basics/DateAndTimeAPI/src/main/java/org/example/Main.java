package org.example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("EVENT MANAGEMENT SYSTEM DEMO\n");

        EventManagementSystem ems = new EventManagementSystem(ZoneId.of("America/New_York"));

        System.out.println("1. Creating Events:");
        System.out.println("==================");

        Event meeting1 = ems.createEvent("Team Meeting",
                LocalDateTime.of(2026, 3, 20, 10, 0),
                LocalDateTime.of(2026, 3, 20, 11, 0),
                ZoneId.of("America/New_York"), false, null);

        Event meeting2 = ems.createEvent("Client Call",
                LocalDateTime.of(2026, 3, 20, 14, 0),
                LocalDateTime.of(2026, 3, 20, 15, 30),
                ZoneId.of("Europe/London"), false, null);

        // Recurring event
        Event dailyStandup = ems.createEvent("Daily Standup",
                LocalDateTime.of(2026, 3, 18, 9, 0),
                LocalDateTime.of(2026, 3, 18, 9, 30),
                ZoneId.of("America/New_York"),
                true,
                Duration.ofDays(1));

        System.out.println("\n2. Events for March 20, 2024:");
        System.out.println("=============================");
        List<Event> march20Events = ems.getEventsForDate(LocalDate.of(2024, 3, 20),
                ZoneId.of("America/New_York"));
        march20Events.forEach(System.out::println);

        System.out.println("\n3. Conflict Detection:");
        System.out.println("=====================");
        boolean hasConflict = ems.hasConflict(
                LocalDateTime.of(2024, 3, 20, 10, 30),
                LocalDateTime.of(2024, 3, 20, 11, 30),
                ZoneId.of("America/New_York"),
                null
        );
        System.out.println("Conflict exists: " + hasConflict);

        System.out.println("\n4. Available Time Slots for March 20:");
        System.out.println("====================================");
        List<TimeSlot> availableSlots = ems.findAvailableSlots(
                LocalDate.of(2024, 3, 20),
                Duration.ofMinutes(60),
                ZoneId.of("America/New_York")
        );
        availableSlots.forEach(System.out::println);

        System.out.println("\n5. Rescheduling Event:");
        System.out.println("=====================");
        ems.rescheduleEvent(meeting1,
                LocalDateTime.of(2024, 3, 20, 16, 0),
                LocalDateTime.of(2024, 3, 20, 17, 0),
                ZoneId.of("America/New_York"));

        System.out.println("\n6. Cancelling Event:");
        System.out.println("===================");
        ems.cancelEvent(meeting1);

        System.out.println("\n7. Timezone Handling:");
        System.out.println("====================");
        ems.displayEventInTimeZone(meeting1, ZoneId.of("Asia/Tokyo"));
        ems.displayEventInTimeZone(meeting1, ZoneId.of("Europe/London"));

        System.out.println("\n8. Final Event List:");
        System.out.println("===================");
        ems.displayAllEvents();
    }
}