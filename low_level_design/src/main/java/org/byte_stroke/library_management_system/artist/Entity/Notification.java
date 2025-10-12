package org.byte_stroke.library_management_system.artist.Entity;

import java.util.Date;

public abstract class Notification {
    String notificationId;
    Date created;
    String content;

    protected abstract void sendNotification();


}
