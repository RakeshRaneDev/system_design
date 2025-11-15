package org.byte_stroke.parking_lot.model;

public class Compact extends ParkingSpot{
    public Compact(int id) {
        super(id);
    }

    @Override
    public boolean assignVehicle(Vehicle vehicle) {
        if(isFree){
            System.out.println("Allocated compact slot " + getId() + " to " + vehicle.getLicensePlate());
            this.vehicle = vehicle;
            isFree = false;
        }
        return false;
    }
}
