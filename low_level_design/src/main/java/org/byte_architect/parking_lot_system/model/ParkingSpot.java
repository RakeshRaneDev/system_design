package org.byte_architect.parking_lot_system.model;

import org.byte_architect.parking_lot_system.enums.SpotType;

public class ParkingSpot {
    private final String id;
    private final SpotType type;
    private boolean available;
    private Vehicle vehicle;


    public ParkingSpot(String id, SpotType type) {
        this.id = id;
        this.type = type;
        this.available = true;
    }


    public synchronized boolean assignVehicle(Vehicle v) {
        if (!available) return false;
        this.vehicle = v;
        this.available = false;
        return true;
    }


    public synchronized void removeVehicle() {
        this.vehicle = null;
        this.available = true;
    }


    public boolean isAvailable() { return available; }
    public SpotType getType() { return type; }
    public String getId() { return id; }
}
