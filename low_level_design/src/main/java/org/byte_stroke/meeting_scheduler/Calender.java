package org.byte_stroke.meeting_scheduler;

import java.util.ArrayList;
import java.util.List;

public class Calender {
    private List<Meeting> meetings = new ArrayList<>();

    public void addMeeting(Meeting meeting){
        meetings.add(meeting);

    }

    public void removeMeeting(Meeting meeting){
          meetings.removeIf(m-> m.getId() == meeting.getId());
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
