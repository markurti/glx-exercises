package org.example;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class EventManagementSystem {
    private List<Event> events;
    private ZoneId systemTimeZone;

    public EventManagementSystem(ZoneId systemTimeZone) {
        this.events = new ArrayList<>();
        this.systemTimeZone = systemTimeZone;
    }

    public EventManagementSystem() {
        this(ZoneId.systemDefault());
    }

    public Event createEvent(String eventName, LocalDateTime startDate, LocalDateTime endDate, ZoneId timeZone,
                             boolean recurring, Duration recurrenceInterval) {
        // Validation
        if (startDate.isAfter(endDate) || startDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        if (recurring & recurrenceInterval == null) {
            throw new IllegalArgumentException("Recurrence interval must be specified for recurring events");
        }

        Event event = new Event(eventName, startDate, endDate, timeZone, recurring, recurrenceInterval);
        events.add(event);

        System.out.println("Created event: " + event);
        return event;
    }

    public List<Event> getEventsForDate(LocalDate date, ZoneId targetTimeZone) {
        List<Event> dayEvents = new ArrayList<>();

        for (Event event : events) {
            // For recurring events generate instances and check
            if (event.isRecurring()) {
                List<Event> recurringInstances = event.generateRecurringInstances(date, date);
                dayEvents.addAll(recurringInstances);
            } else {
                // For regular events check if the date falls on the given date
                ZonedDateTime eventStartInTargetZone = event.getStartDateInZone(targetTimeZone);
                if (eventStartInTargetZone.toLocalDate().equals(date)) {
                    dayEvents.add(event);
                }
            }
        }

        // Sort by start time
        dayEvents.sort((e1, e2) -> {
            ZonedDateTime start1 = e1.getStartDateInZone(targetTimeZone);
            ZonedDateTime start2 = e2.getStartDateInZone(targetTimeZone);
            return start1.compareTo(start2);
        });

        return dayEvents;
    }


}
