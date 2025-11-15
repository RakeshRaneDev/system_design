package org.byte_architect.parking_lot_system.model;

import org.byte_architect.parking_lot_system.enums.SpotType;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Floor {
    private final int floorNumber;
    private final List<ParkingSpot> spots;
    private final Lock floorLock = new ReentrantLock();


    public Floor(int floorNumber, List<ParkingSpot> spots) {
        this.floorNumber = floorNumber;
        this.spots = spots;
    }


    public ParkingSpot getAvailableSpot(Vehicle vehicle) {
        floorLock.lock();
        try {
            for (ParkingSpot spot : spots) {
                if (spot.isAvailable() && isCompatible(spot, vehicle)) {
                    return spot;
                }
            }
        } finally {
            floorLock.unlock();
        }
        return null;
    }


    private boolean isCompatible(ParkingSpot spot, Vehicle v) {
        SpotType st = spot.getType();
        switch (v.getType()) {
            case CAR: return st == SpotType.COMPACT || st == SpotType.LARGE;
            case TRUCK: return st == SpotType.LARGE;
            case ELECTRIC_CAR: return st == SpotType.EV || st == SpotType.LARGE;
            case MOTORCYCLE: return st == SpotType.COMPACT || st == SpotType.LARGE || st == SpotType.HANDICAPPED;
            default: return false;
        }
    }


    public int getFloorNumber() { return floorNumber; }
    public List<ParkingSpot> getSpots() { return spots; }
}
