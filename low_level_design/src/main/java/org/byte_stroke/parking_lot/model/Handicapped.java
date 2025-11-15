package org.byte_stroke.parking_lot.model;

public class Handicapped extends ParkingSpot {


    public Handicapped(int id) {
        super(id);
    }

    @Override
    public boolean assignVehicle(Vehicle vehicle) {
        if(isFree){
            System.out.println("Allocated handicapped slot " + getId() + " to " + vehicle.getLicensePlate());
            this.vehicle = vehicle;
            isFree = false;
        }
        return false;
    }
}
