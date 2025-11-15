package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.enums.VehicleType;

public class Car extends Vehicle{

    public Car(String licensePlate){
        super(licensePlate, VehicleType.CAR);
    }
}
