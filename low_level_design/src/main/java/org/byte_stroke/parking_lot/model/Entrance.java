package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.ParkingLot;
import org.byte_stroke.parking_lot.entity.ParkingTicket;

public class Entrance {
    private int id ;
    public Entrance(int id){
        this.id = id;
    }


    public ParkingTicket getTicket(Vehicle v){
        return ParkingLot.getInstance().parkVehicle(v);

    }
}
