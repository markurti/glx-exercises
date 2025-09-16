package org.example.AbstractFactory.SubFactory;

import org.example.AbstractFactory.AbstractVehicleFactory;
import org.example.Entity.AbstractVehicle;
import org.example.Entity.Bike;
import org.example.Entity.Car;

public class LandVehicleFactory implements AbstractVehicleFactory {
    @Override
    public AbstractVehicle createCar() {
        return new Car();
    }

    @Override
    public AbstractVehicle createBike() {
        return new Bike();
    }

    @Override
    public AbstractVehicle createBoat() {
        throw new UnsupportedOperationException("Air factory cannot create boats.");
    }

    @Override
    public AbstractVehicle createSubmarine() {
        throw new UnsupportedOperationException("Air factory cannot create submarines.");
    }

    @Override
    public AbstractVehicle createAirplane() {
        throw new UnsupportedOperationException("Air factory cannot create airplanes.");
    }

    @Override
    public AbstractVehicle createHelicopter() {
        throw new UnsupportedOperationException("Air factory cannot create helicopters.");
    }
}
