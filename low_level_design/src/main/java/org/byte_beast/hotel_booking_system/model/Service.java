package org.byte_beast.hotel_booking_system.model;


import java.time.LocalDateTime;

public abstract class Service {
    protected double prize;
    private LocalDateTime issueAt;

    public Service(double prize) {
        this.prize = prize;
        this.issueAt = LocalDateTime.now();
    }
}
