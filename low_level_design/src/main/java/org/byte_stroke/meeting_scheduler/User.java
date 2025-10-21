package org.byte_stroke.meeting_scheduler;
import java.util.*;

import java.util.List;

public class User {
    private String name;
    private String email;
    private Calender calender;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.calender = new Calender();
    }

    public List<Meeting> viewMeeting(){
        return calender.getMeetings();

    }

    public void respondInvitation(Meeting meeting , RSVPStatus status){
        meeting.updateParticipateStatus(this, status);
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calender getCalender() {
        return calender;
    }

    public void setCalender(Calender calender) {
        this.calender = calender;
    }
}
