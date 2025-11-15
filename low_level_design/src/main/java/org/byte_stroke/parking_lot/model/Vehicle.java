package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.entity.ParkingTicket;
import org.byte_stroke.parking_lot.enums.VehicleType;

public abstract class Vehicle {
    private String licensePlate;
    private ParkingTicket ticket;

    private VehicleType vehicleType;
    protected Vehicle(String licensePlate,VehicleType vehicleType ){
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }


    public  void AssignTicket(ParkingTicket parkingTicket){
        this.ticket = parkingTicket;
    }

    public  ParkingTicket getTicket(){
        return this.ticket;
    }
    public  String getLicensePlate(){
        return this.licensePlate;
    }

}
