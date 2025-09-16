package org.example.AbstractFactory.SubFactory;

import org.example.AbstractFactory.AbstractVehicleFactory;
import org.example.Entity.AbstractVehicle;
import org.example.Entity.Boat;
import org.example.Entity.Submarine;

public class WaterVehicleFactory implements AbstractVehicleFactory {

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
        return new Boat();
    }

    @Override
    public AbstractVehicle createSubmarine() {
        return new Submarine();
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
