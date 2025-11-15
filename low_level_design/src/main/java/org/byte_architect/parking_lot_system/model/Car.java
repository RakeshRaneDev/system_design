package org.byte_architect.parking_lot_system.model;

import org.byte_architect.parking_lot_system.enums.VehicleType;

public class Car extends Vehicle{
    public Car(String licensePlate) { super(licensePlate, VehicleType.CAR); }
}
