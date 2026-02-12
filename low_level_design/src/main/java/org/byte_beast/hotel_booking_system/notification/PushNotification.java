package org.byte_beast.hotel_booking_system.notification;

public class PushNotification extends Notification {

   public PushNotification(){
        super();
    }
    @Override
    public void sendNotification(String message) {
        System.out.println("[PushNotification] : "+ message);
    }
}
