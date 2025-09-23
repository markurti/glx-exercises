package com.logistics.models;

import java.time.LocalDateTime;

public class Route {
    private Long id;
    private Location origin;
    private Location destination;
    private float distance;

    public Route(Location origin, Location destination, float distance) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
    }

    public LocalDateTime calculateETA() {
        // Simple calculation: 60 km/h average speed
        float hours = distance / 60;
        return LocalDateTime.now().plusHours((long)hours);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Location getOrigin() { return origin; }
    public void setOrigin(Location origin) { this.origin = origin; }

    public Location getDestination() { return destination; }
    public void setDestination(Location destination) { this.destination = destination; }

    public float getDistance() { return distance; }
    public void setDistance(float distance) { this.distance = distance; }
}
