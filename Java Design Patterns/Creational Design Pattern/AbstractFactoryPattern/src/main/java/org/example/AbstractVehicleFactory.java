package org.example;

public interface AbstractVehicleFactory {
    public AbstractVehicle createCar();
    public AbstractVehicle createBike();
    public AbstractVehicle createBoat();
    public AbstractVehicle createSubmarine();
    public AbstractVehicle createAirplane();
    public AbstractVehicle createHelicopter();
}
