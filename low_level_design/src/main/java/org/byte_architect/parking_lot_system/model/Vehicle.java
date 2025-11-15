package org.byte_architect.parking_lot_system.model;

import org.byte_architect.parking_lot_system.enums.VehicleType;

public abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType type;


    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }


    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }
}
