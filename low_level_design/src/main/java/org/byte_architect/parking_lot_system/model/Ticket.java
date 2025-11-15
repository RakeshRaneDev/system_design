package org.byte_architect.parking_lot_system.model;

import org.byte_stroke.parking_lot.enums.PaymentStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private PaymentStatus status;


    public Ticket(String id, Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = id;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }


    public long calculateDurationHours() {
        if (exitTime == null) return 0;
        return Duration.between(entryTime, exitTime).toHours();
    }


    public void close(double amount) {
        this.exitTime = LocalDateTime.now();
        this.amount = amount;
        this.status = PaymentStatus.COMPLETED;
    }


    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public double getAmount() { return amount; }
}
