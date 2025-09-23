package com.logistics.models;

public class Shipment {
    private Long id;
    private Address sender;
    private Address receiver;
    private float weight;
    private Status status;
    private Carrier carrier;
    private Warehouse warehouse;

    public Shipment(Address sender, Address receiver, float weight) {
        this.sender = sender;
        this.receiver = receiver;
        this.weight = weight;
        this.status = new Status(100, "Created");
    }

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }

    public float calculateShippingCost() {
        if (carrier != null && carrier.getShippingRates() != null) {
            // Default to ground shipping
            return weight * carrier.getShippingRates().getGround();
        }
        return weight * 5.0f; // Default rate
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Address getSender() { return sender; }
    public void setSender(Address sender) { this.sender = sender; }

    public Address getReceiver() { return receiver; }
    public void setReceiver(Address receiver) { this.receiver = receiver; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Carrier getCarrier() { return carrier; }
    public void setCarrier(Carrier carrier) { this.carrier = carrier; }

    public Warehouse getWarehouse() { return warehouse; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }

    @Override
    public String toString() {
        return "Shipment #" + id + " (" + weight + "kg) - " + status;
    }
}
