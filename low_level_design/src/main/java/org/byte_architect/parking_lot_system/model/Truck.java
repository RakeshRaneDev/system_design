package org.byte_architect.parking_lot_system.model;

import org.byte_architect.parking_lot_system.enums.VehicleType;

public class Truck extends Vehicle {
    public Truck(String licensePlate) { super(licensePlate, VehicleType.TRUCK); }
}
