package org.byte_stroke.meeting_scheduler;

import org.byte_stroke.meeting_scheduler.Meeting;
import org.byte_stroke.meeting_scheduler.User;

import java.util.Date;

public class Notification {

    private static int notificationId = 1;
    private int id;
    private Date createOn;
    private String content;

    public Notification(String content) {
        this.id = notificationId++;
        this.createOn = new Date();
        this.content = content;
    }

    public void sendInvitation(User user, Meeting meeting){
        System.out.println("new Meeting " + meeting + "invitation " + this.content);
        System.out.println("The notificatin has been send to user" + user);

    }

    public void sendCancelNotification(User user, Meeting meeting){
        System.out.println("Meeting " + meeting + "canceled " + this.content);
        System.out.println("The notificatin has been send to user" + user);


    }
}
