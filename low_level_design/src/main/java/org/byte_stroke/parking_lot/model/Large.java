package org.byte_stroke.parking_lot.model;

public class Large extends ParkingSpot{
    public Large(int id) {
        super(id);
    }

    @Override
    public boolean assignVehicle(Vehicle vehicle) {
        if(isFree){
            System.out.println("Allocated large slot " + getId() + " to " + vehicle.getLicensePlate());
            this.vehicle = vehicle;
            isFree = false;
        }
        return false;
    }
}
