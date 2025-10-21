package org.byte_stroke.meeting_scheduler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MeetingSchedulerApplication {
    public static void main(String[] args){
        MeetingRoom meetingRoomA = new MeetingRoom(1, 10);
        MeetingRoom meetingRoomB = new MeetingRoom(1, 5);
        List<MeetingRoom> rooms = Arrays.asList(meetingRoomA,meetingRoomB);


        // setup user
        User rakesh = new User("Rakesh", " rakesh.com");
        User sneha = new User("Sneha", " senha.com");
        User malti = new User("malti", "malti.com");

        List<User> users = Arrays.asList(rakesh,sneha,malti);

        //setup scheduler

        MeetingScheduler scheduler = new MeetingScheduler(malti, rooms);

        // senoroes1

        LocalDateTime startDate = LocalDateTime.of(2025, 10,12, 10,00);
        LocalDateTime endDate = LocalDateTime.of(2025, 10,12, 11,00);
        Interval interval = new Interval(startDate, endDate);
        scheduler.scheduleMeeting(users, interval, " design review");

    }
}
