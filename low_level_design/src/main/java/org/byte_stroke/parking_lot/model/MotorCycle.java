package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.enums.VehicleType;

public class MotorCycle  extends Vehicle{
    public MotorCycle(String licensePlate){
        super(licensePlate, VehicleType.VAN);
    }
}
