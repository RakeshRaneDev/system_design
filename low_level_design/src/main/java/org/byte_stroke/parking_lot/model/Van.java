package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.enums.VehicleType;

public class Van extends Vehicle{

   public Van(String licensePlate, VehicleType vehicleType ){
        super(licensePlate, VehicleType.VAN);
    }
}
