package org.byte_beast.hotel_booking_system.model;

import org.byte_beast.hotel_booking_system.enums.BookingStatus;

import java.time.LocalDateTime;

public class RoomBooking {
    private String bookingNumber ;
    private LocalDateTime checkIn;
    private int durationInDays;
    private LocalDateTime checkOut;
    private BookingStatus bookingStatus;

    private Room room;
    private int guestId;

    public RoomBooking(String bookingNumber, LocalDateTime checkIn, int durationInDays, LocalDateTime checkOut, BookingStatus bookingStatus, Room room, int guestId) {
        this.bookingNumber = bookingNumber;
        this.checkIn = checkIn;
        this.durationInDays = durationInDays;
        this.checkOut = checkOut;
        this.bookingStatus = bookingStatus;
        this.room = room;
        this.guestId = guestId;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }
}
