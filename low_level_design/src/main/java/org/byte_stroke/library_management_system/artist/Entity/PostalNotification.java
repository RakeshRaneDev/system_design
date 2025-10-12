package org.byte_stroke.library_management_system.artist.Entity;

public class PostalNotification extends  Notification{

    private Address address;

    public PostalNotification(Address address) {
        this.address = address;
    }

    @Override
    protected void sendNotification() {

    }
}
