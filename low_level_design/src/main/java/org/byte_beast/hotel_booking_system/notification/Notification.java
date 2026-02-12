package org.byte_beast.hotel_booking_system.notification;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Notification {


    private UUID id;
    private LocalDateTime createdAt;
    public Notification() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }


   public abstract void sendNotification(String message);
}
