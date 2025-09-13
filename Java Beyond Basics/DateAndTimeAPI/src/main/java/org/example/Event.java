package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ZoneId timeZone;
    private boolean recurring;
    private Duration recurrenceInterval;

    // Constructor
    public Event(String eventName, LocalDateTime startDate, LocalDateTime endDate, ZoneId timeZone, boolean recurring, Duration recurrenceInterval) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeZone = timeZone;
        this.recurring = recurring;
        this.recurrenceInterval = recurrenceInterval;
    }

    // Constructor for non-recurring events
    public Event(String eventName, LocalDateTime startDate, LocalDateTime endDate, ZoneId timeZone) {
        this(eventName, startDate, endDate, timeZone, false, null);
    }

    // Getters and Setters
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public ZoneId getTimeZone() { return timeZone; }
    public void setTimeZone(ZoneId timeZone) { this.timeZone = timeZone; }

    public boolean isRecurring() { return recurring; }
    public void setRecurring(boolean recurring) { this.recurring = recurring; }

    public Duration getRecurrenceInterval() { return recurrenceInterval; }
    public void setRecurrenceInterval(Duration recurrenceInterval) { this.recurrenceInterval = recurrenceInterval; }

    // Helper method to get event duration
    public Duration getDuration() {
        return Duration.between(startDate, endDate);
    }

    // Convert to specific timezone for comparison
    public ZonedDateTime getStartDateInZone(ZoneId targetZone) {
        return ZonedDateTime.of(startDate, targetZone).withZoneSameInstant(timeZone);
    }

    public ZonedDateTime getEndDateInZone(ZoneId targetZone) {
        return ZonedDateTime.of(endDate, targetZone).withZoneSameInstant(timeZone);
    }

    // Generate recurring event instances within a date range
    public List<Event> generateRecurringInstances(LocalDate fromDate, LocalDate toDate) {
        List<Event> instances = new ArrayList<>();

        if (!recurring || recurrenceInterval == null) {
            // Include original event if it falls withing the range
            LocalDate eventDate = startDate.toLocalDate();
            if (!eventDate.isBefore(fromDate) && eventDate.isAfter(toDate)) {
                instances.add(this);
            }
            return instances;
        }

        LocalDateTime currentStart = startDate;
        LocalDateTime currentEnd = endDate;

        while (!currentStart.toLocalDate().isAfter(toDate)) {
            if (!currentStart.toLocalDate().isBefore(fromDate)) {
                Event recurringEvent = new Event(eventName + "(Recurring)", currentStart, currentEnd, timeZone);
                instances.add(recurringEvent);
            }

            currentStart = currentStart.plus(recurrenceInterval);
            currentEnd = currentEnd.plus(recurrenceInterval);
        }

        return instances;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("Event={name='%s', start=%s, end=%s, zone=%s, recurring=%s}", eventName, startDate.format(formatter),
                endDate.format(formatter), timeZone.getId(), recurring);
    }


}
