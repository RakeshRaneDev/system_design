package org.byte_stroke.parking_lot.model;

public class Admin extends Account{
    public  boolean addParkingSpot(ParkingSpot spot){
        return true;
    }

    public  boolean addDisplayBoard(DisplayBoard spot){
        return true;
    }
    public  boolean addEntrance(Entrance spot){
        return true;
    }
    public  boolean addExit(Exit spot){
        return true;
    }

    @Override
    public boolean resetPassword() {
        return false;
    }
}
