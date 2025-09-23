package com.logistics.application;

import com.logistics.models.*;
import com.logistics.repository.*;
import com.logistics.database.IDatabaseConnection;
import java.util.ArrayList;
import java.util.List;

public class LogisticsApplication implements ILogisticsService {
    private List<Shipment> shipments;
    private List<Warehouse> warehouses;
    private List<Carrier> carriers;
    private List<Route> routes;
    private List<Vehicle> fleet;
    private List<Customer> customers;

    private final ShipmentRepository shipmentRepo;
    private final WarehouseRepository warehouseRepo;
    private final CarrierRepository carrierRepo;
    private final CustomerRepository customerRepo;

    public LogisticsApplication(IDatabaseConnection dbConnection) {
        this.shipments = new ArrayList<>();
        this.warehouses = new ArrayList<>();
        this.carriers = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.fleet = new ArrayList<>();
        this.customers = new ArrayList<>();

        // Initialize repositories with dependency injection
        this.shipmentRepo = new ShipmentRepository(dbConnection);
        this.warehouseRepo = new WarehouseRepository(dbConnection);
        this.carrierRepo = new CarrierRepository(dbConnection);
        this.customerRepo = new CustomerRepository(dbConnection);

        // Load data from database
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        this.shipments = shipmentRepo.findAll();
        this.warehouses = warehouseRepo.findAll();
        this.carriers = carrierRepo.findAll();
        this.customers = customerRepo.findAll();
    }

    @Override
    public Shipment createShipment(Address sender, Address receiver, float weight) {
        Shipment shipment = new Shipment(sender, receiver, weight);
        shipment = shipmentRepo.save(shipment);
        shipments.add(shipment);
        System.out.println("Shipment created with ID: " + shipment.getId());
        return shipment;
    }

    @Override
    public void assignCarrier(Shipment shipment, Carrier carrier) {
        shipment.setCarrier(carrier);
        shipmentRepo.update(shipment);
        System.out.println("Carrier assigned to shipment #" + shipment.getId());
    }

    @Override
    public void allocateWarehouse(Shipment shipment, Warehouse warehouse) {
        shipment.setWarehouse(warehouse);
        shipmentRepo.update(shipment);
        System.out.println("Warehouse allocated to shipment #" + shipment.getId());
    }

    @Override
    public Route createRoute(Location origin, Location destination) {
        // Calculate distance using simple Euclidean formula
        float dx = destination.getLatitude() - origin.getLatitude();
        float dy = destination.getLongitude() - origin.getLongitude();
        float distance = (float) Math.sqrt(dx * dx + dy * dy) * 111; // Convert to km

        Route route = new Route(origin, destination, distance);
        routes.add(route);
        System.out.println("Route created with distance: " + distance + " km");
        return route;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        fleet.add(vehicle);
        System.out.println("Vehicle added: " + vehicle.getPlateNumber());
    }

    @Override
    public void removeVehicle(Vehicle vehicle) {
        fleet.remove(vehicle);
        System.out.println("Vehicle removed: " + vehicle.getPlateNumber());
    }

    @Override
    public void addCustomer(Customer customer) {
        customer = customerRepo.save(customer);
        customers.add(customer);
        System.out.println("Customer added: " + customer.getName());
    }

    @Override
    public void removeCustomer(Customer customer) {
        if (customer.getId() != null) {
            customerRepo.delete(customer.getId());
            customers.remove(customer);
            System.out.println("Customer removed: " + customer.getName());
        }
    }

    @Override
    public List<Shipment> getAllShipments() {
        return new ArrayList<>(shipments);
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        return new ArrayList<>(warehouses);
    }

    @Override
    public List<Carrier> getAllCarriers() {
        return new ArrayList<>(carriers);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
}
