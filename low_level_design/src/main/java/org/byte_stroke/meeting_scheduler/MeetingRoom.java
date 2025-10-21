package org.byte_stroke.meeting_scheduler;

import org.byte_stroke.meeting_scheduler.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeetingRoom {
    private int id;
    private int capacity;
    private List<Interval> listOfMeeting;

    private boolean isAvailable;

    public MeetingRoom(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.listOfMeeting = new ArrayList<>();
    }

    public  void bookInterval(Interval interval){
        listOfMeeting.add(interval);

    }

    public void releaseInterval(Interval interval){
            listOfMeeting.removeIf(vi-> vi.getStartTime().equals(interval.getEndTime())
                    && vi.getEndTime().equals(interval.getEndTime()));
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Interval> getListOfMeeting() {
        return listOfMeeting;
    }

    public void setListOfMeeting(List<Interval> listOfMeeting) {
        this.listOfMeeting = listOfMeeting;
    }

    public boolean isAvailable(int capacity, Interval interval) {
        if(this.capacity<capacity){
            return false;
        }
        if(listOfMeeting.isEmpty()){
            return true;
        }

        List<Interval>  conflict = listOfMeeting.stream()
                .filter(booked -> isConflict(booked, interval)).collect(Collectors.toList());
        if(conflict.isEmpty()){
            return true;
        }
        return false;
    }

    private boolean isConflict(Interval booked, Interval interval){
        if(booked.getEndTime().isAfter(interval.getStartTime())
                && (booked.getEndTime().isBefore(interval.getEndTime()))){
            return true;
        }

        if(booked.getStartTime().isAfter(interval.getStartTime())
                && (booked.getStartTime().isBefore(interval.getEndTime()))){
            return true;
        }
        return false;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
