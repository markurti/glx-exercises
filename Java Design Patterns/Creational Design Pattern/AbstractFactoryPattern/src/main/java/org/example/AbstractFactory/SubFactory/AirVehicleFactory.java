package org.example.AbstractFactory.SubFactory;

import org.example.AbstractFactory.AbstractVehicleFactory;
import org.example.Entity.AbstractVehicle;
import org.example.Entity.Ariplane;
import org.example.Entity.Helicopter;

public class AirVehicleFactory implements AbstractVehicleFactory {

    @Override
    public AbstractVehicle createCar() {
        throw new UnsupportedOperationException("Air factory cannot create cars.");
    }

    @Override
    public AbstractVehicle createBike() {
        throw new UnsupportedOperationException("Air factory cannot create bikes.");
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
        return new Ariplane();
    }

    @Override
    public AbstractVehicle createHelicopter() {
        return new Helicopter();
    }
}
