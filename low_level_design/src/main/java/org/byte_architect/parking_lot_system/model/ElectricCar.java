package org.byte_architect.parking_lot_system.model;

import org.byte_architect.parking_lot_system.enums.VehicleType;

public class ElectricCar extends Vehicle {
    private int batteryLevel;


    public ElectricCar(String licensePlate, int batteryLevel) {
        super(licensePlate, VehicleType.ELECTRIC_CAR);
        this.batteryLevel = batteryLevel;
    }


    public int getBatteryLevel() { return batteryLevel; }
}
