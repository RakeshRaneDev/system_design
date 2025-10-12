package org.byte_stroke.library_management_system.artist.Entity;

public class EmailNotification extends Notification{
   private String email;

    public EmailNotification(String email) {
        this.email = email;
    }

    @Override
    protected void sendNotification() {

    }
}
