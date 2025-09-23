package com.logistics.application;

import com.logistics.models.*;
import java.util.List;

public interface ILogisticsService {
    Shipment createShipment(Address sender, Address receiver, float weight);
    void assignCarrier(Shipment shipment, Carrier carrier);
    void allocateWarehouse(Shipment shipment, Warehouse warehouse);
    Route createRoute(Location origin, Location destination);
    void addVehicle(Vehicle vehicle);
    void removeVehicle(Vehicle vehicle);
    void addCustomer(Customer customer);
    void removeCustomer(Customer customer);
    List<Shipment> getAllShipments();
    List<Warehouse> getAllWarehouses();
    List<Carrier> getAllCarriers();
    List<Customer> getAllCustomers();
}
