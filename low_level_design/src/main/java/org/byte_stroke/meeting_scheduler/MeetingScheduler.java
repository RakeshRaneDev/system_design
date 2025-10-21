package org.byte_stroke.meeting_scheduler;

import java.util.List;
import java.util.Random;

public class MeetingScheduler {

    private User organizer;
    private Calender calender;
    private List<MeetingRoom> rooms ;

    public MeetingScheduler(User organizer, List<MeetingRoom> rooms) {
        this.organizer = organizer;
        this.calender = organizer.getCalender();
        this.rooms = rooms;
    }

    public Meeting scheduleMeeting(List<User> users, Interval interval, String subject){

        MeetingRoom availableRoom = checkRoomAvailability(users.size(),interval);
        if(availableRoom ==null){
            System.out.println("Room not available");
            return null;
        }

        bookRoom(availableRoom, interval);

        Meeting meeting = new Meeting(users, availableRoom, subject, interval);
        // send to all user calender

        for(User usr:users){
            usr.getCalender().addMeeting(meeting);
        }

        organizer.getCalender().addMeeting(meeting);


        for(User user:users){
            Notification notification = new Notification(subject);
            notification.sendInvitation(user, meeting);
            RSVPStatus status = new Random().nextBoolean()?RSVPStatus.ACCEPTED: RSVPStatus.REJECTED;

        }
        System.out.println("meeting schedule successfully\n");
        return  meeting;

    }

    public boolean cancelMeeting(Meeting meeting){
        releaseRoom(meeting.getMeetingRoom(), meeting.getInterval());
        for(User user: meeting.getAcceptedUsers()){
            user.getCalender().removeMeeting(meeting);
            Notification notification = new Notification("canceled meeting"+ meeting.getSubject());
            notification.sendCancelNotification(user, meeting);
        }
        System.out.println(" meeting canceled");
        return true;

    }

    public MeetingRoom checkRoomAvailability(int capacity, Interval interval ){
        for(MeetingRoom room: rooms){
            if(room.isAvailable(capacity, interval)){
                return room;
            }
        }
        return null;
    }

    public  boolean bookRoom(MeetingRoom meetingRoom, Interval interval){
        meetingRoom.bookInterval(interval);
        return true;

    }
    public boolean releaseRoom(MeetingRoom meetingRoom, Interval interval){
        meetingRoom.releaseInterval(interval);
        return true;
    }



}
