package org.byte_stroke.parking_lot.model;

public class MotorCycleSlot extends ParkingSpot{
    public MotorCycleSlot(int id) {
        super(id);
    }

    @Override
    public boolean assignVehicle(Vehicle vehicle) {
        if(isFree){
            System.out.println("Allocated motor cycle slot slot " + getId() + " to " + vehicle.getLicensePlate());
            this.vehicle = vehicle;
            isFree = false;
        }
        return false;
    }
}
