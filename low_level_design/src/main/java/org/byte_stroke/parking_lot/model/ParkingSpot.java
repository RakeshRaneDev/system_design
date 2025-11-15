package org.byte_stroke.parking_lot.model;

public abstract class ParkingSpot {
    private int id ;
    protected boolean isFree = true;
    protected Vehicle vehicle;
    public ParkingSpot(int id){
       this.id = id;
    }

    public abstract boolean assignVehicle(Vehicle vehicle);

    public boolean removeVehicle(){
        if(!isFree && vehicle!=null){
            System.out.println("[ParkingSpot] slot " + id +  " free ( " + id + ")");
            vehicle = null;
            isFree = true;
            return true;
        }
        return false;
    }

    public int getId(){
        return this.id;
    }

    public boolean isSpotFree(){
        return this.isFree;
    }

}
