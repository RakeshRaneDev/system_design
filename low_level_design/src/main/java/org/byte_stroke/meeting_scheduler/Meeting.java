package org.byte_stroke.meeting_scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class Meeting {

    private static int nextId = 1;
    private int id;
    private Map<User, RSVPStatus> participate = new HashMap<>();
    private MeetingRoom meetingRoom;
    private String subject;

    private Interval interval;

    public Meeting(List<User>users, MeetingRoom meetingRoom, String subject, Interval interval) {
        this.id = nextId++;
        for(User user: users){
            participate.put(user, RSVPStatus.PENDING);
        }
        this.meetingRoom = meetingRoom;
        this.subject = subject;
        this.interval = interval;
    }

    public void addParticipate(User user, RSVPStatus status){
        if(participate.containsKey(user)){
            System.out.println("user already added to a meeting");
            return;
        }
        participate.put(user, status);
    }

    public void updateParticipateStatus(User user, RSVPStatus staus){
        if(participate.containsKey(user)){
            participate.put(user, staus);
        }
    }

    public List<User> getAcceptedUsers(){
        List<User> acceptedUsers = new ArrayList<>();
        for(Map.Entry entry: participate.entrySet()){
            if(entry.getValue().equals(RSVPStatus.ACCEPTED)){
                acceptedUsers.add((User)entry.getKey());
            }
        }
        return acceptedUsers;
    }

    public List<User> getRejectedUsers(){
        List<User> rejectedUsers = new ArrayList<>();
        for(Map.Entry entry: participate.entrySet()){
            if(entry.getValue().equals(RSVPStatus.REJECTED)){
                rejectedUsers.add((User)entry.getKey());
            }
        }
        return rejectedUsers;
    }

    public List<User> getPendingUsers(){
        List<User> pendingUsers = new ArrayList<>();
        for(Map.Entry entry: participate.entrySet()){
            if(entry.getValue().equals(RSVPStatus.PENDING)){
                pendingUsers.add((User)entry.getKey());
            }
        }
        return pendingUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<User, RSVPStatus> getParticipate() {
        return participate;
    }


    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
}
