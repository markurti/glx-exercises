package org.example;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean hasConflict(LocalDateTime newStartDate, LocalDateTime newEndDate, ZoneId newTimeZone, Event excludeEvent) {
        // Convert  new event times to timezone for comparison
        ZonedDateTime newStartInSystem = ZonedDateTime.of(newStartDate, systemTimeZone).withZoneSameInstant(systemTimeZone);
        ZonedDateTime newEndInSystem = ZonedDateTime.of(newEndDate, systemTimeZone).withZoneSameInstant(systemTimeZone);

        for (Event event : events) {
            if (event.equals(excludeEvent)) {
                continue; // skip the event being rescheduled
            }

            // Handle recurring events by generating instances in the relevant time period
            LocalDate checkStart = newStartInSystem.toLocalDate().minusDays(1);
            LocalDate checkEnd = newEndInSystem.toLocalDate().plusDays(1);

            List<Event> eventsToCheck = event.isRecurring() ?
                    event.generateRecurringInstances(checkStart, checkEnd) :
                    List.of(event);

            for (Event eventToCheck : eventsToCheck) {
                // Convert existing event to system timezone
                ZonedDateTime existingStartInSystem = eventToCheck.getStartDateInZone(systemTimeZone);
                ZonedDateTime existingEndInSystem = eventToCheck.getEndDateInZone(systemTimeZone);

                // Check for overlap: events overlap if one starts before the other ends
                boolean hasOverlap = newStartInSystem.isBefore(existingEndInSystem) &&
                        newEndInSystem.isAfter(existingStartInSystem);

                if (hasOverlap) {
                    System.out.printf("Conflict detected with event '%s' (%s - %s)%n",
                            eventToCheck.getEventName(),
                            existingStartInSystem.toLocalDateTime(),
                            existingEndInSystem.toLocalDateTime());
                    return true;
                }
            }
        }

        return false;
    }

    public List<TimeSlot> findAvailableSlots(LocalDate date, Duration eventDuration, ZoneId timeZone) {
        List<TimeSlot> availableSlots = new ArrayList<>();

        // Get all events for given date
        List<Event> dayEvents = getEventsForDate(date, timeZone);

        // Convert events to time slots in the target time zone and sort by start time
        List<TimeSlot> busySlots = dayEvents.stream()
                .map(event -> {
                    ZonedDateTime start = event.getStartDateInZone(timeZone);
                    ZonedDateTime end = event.getEndDateInZone(timeZone);
                    return new TimeSlot(start.toLocalDateTime(), end.toLocalDateTime());
                })
                .sorted((s1, s2) -> s1.getStartTime().compareTo(s2.getStartTime()))
                .toList();

        // Define hours while the event is active / day
        LocalDateTime businessStart = date.atTime(9, 0);
        LocalDateTime businessEnd = date.atTime(19, 0);

        LocalDateTime currentTime = businessStart;

        // Find gaps between buys slots
        for (TimeSlot busySlot : busySlots) {
            // If there's a gap before this busy slot
            if (currentTime.isBefore(busySlot.getStartTime())) {
                LocalDateTime slotEnd = busySlot.getStartTime();

                // Check if the available duration is sufficient
                if (Duration.between(currentTime, slotEnd).compareTo(eventDuration) >= 0) {
                    availableSlots.add(new TimeSlot(currentTime, slotEnd));
                }
            }

            // Move current time to the end of this busy slot
            currentTime = busySlot.getEndTime().isAfter(currentTime) ? busySlot.getEndTime() : currentTime;
        }

        // Check for availability after the last event until business end
        if (currentTime.isBefore(businessEnd)) {
            if (Duration.between(currentTime, businessEnd).compareTo(eventDuration) >= 0) {
                availableSlots.add(new TimeSlot(currentTime, businessEnd));
            }
        }

        return availableSlots;
    }

    public boolean rescheduleEvent(Event event, LocalDateTime newStartDate,
                                   LocalDateTime newEndDate, ZoneId newTimeZone) {

        if (!events.contains(event)) {
            System.out.println("Event not found in the system");
            return false;
        }

        // Check for conflicts (excluding the current event)
        if (hasConflict(newStartDate, newEndDate, newTimeZone, event)) {
            System.out.println("Cannot reschedule: New time conflicts with existing events");
            return false;
        }

        // Store old values for logging
        LocalDateTime oldStart = event.getStartDate();
        LocalDateTime oldEnd = event.getEndDate();
        ZoneId oldZone = event.getTimeZone();

        // Update the event
        event.setStartDate(newStartDate);
        event.setEndDate(newEndDate);
        event.setTimeZone(newTimeZone);

        System.out.printf("Event '%s' rescheduled from %s-%s (%s) to %s-%s (%s)%n",
                event.getEventName(), oldStart, oldEnd, oldZone.getId(),
                newStartDate, newEndDate, newTimeZone.getId());

        return true;
    }

    public boolean cancelEvent(Event event) {
        boolean removed = events.remove(event);

        if (removed) {
            System.out.printf("Event '%s' has been cancelled and removed from the system%n",
                    event.getEventName());
        } else {
            System.out.println("Event not found in the system");
        }

        return removed;
    }

    public void displayEventInTimeZone(Event event, ZoneId targetTimeZone) {
        ZonedDateTime startInTarget = event.getStartDateInZone(targetTimeZone);
        ZonedDateTime endInTarget = event.getEndDateInZone(targetTimeZone);

        System.out.printf("Event '%s' in %s timezone: %s to %s%n",
                event.getEventName(),
                targetTimeZone.getId(),
                startInTarget.toLocalDateTime(),
                endInTarget.toLocalDateTime());
    }

    public Event convertEventToTimeZone(Event event, ZoneId targetTimeZone) {
        ZonedDateTime startInTarget = event.getStartDateInZone(targetTimeZone);
        ZonedDateTime endInTarget = event.getEndDateInZone(targetTimeZone);

        return new Event(
                event.getEventName(),
                startInTarget.toLocalDateTime(),
                endInTarget.toLocalDateTime(),
                targetTimeZone,
                event.isRecurring(),
                event.getRecurrenceInterval()
        );
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }

    public int getEventCount() {
        return events.size();
    }

    public void displayAllEvents() {
        if (events.isEmpty()) {
            System.out.println("No events scheduled");
            return;
        }

        System.out.println("All Events:");
        System.out.println("==========");
        events.forEach(System.out::println);
    }
}
