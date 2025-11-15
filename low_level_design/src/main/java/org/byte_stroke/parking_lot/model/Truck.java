package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.enums.VehicleType;

public class Truck extends  Vehicle{

    public Truck(String licensePlate, VehicleType vehicleType ){
        super(licensePlate, VehicleType.VAN);
    }
}
