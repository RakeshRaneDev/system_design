package org.byte_stroke.library_management_system.educative;
import java.util.*;

public abstract class Notification {
    String notificationId, content;
    Date created;
    public Notification(String notificationId, String content) {
        this.notificationId = notificationId; this.content = content;
        this.created = new Date();
    }
    public abstract boolean sendNotification();
}