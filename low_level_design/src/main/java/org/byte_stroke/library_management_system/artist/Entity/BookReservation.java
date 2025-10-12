package org.byte_stroke.library_management_system.artist.Entity;

import org.byte_stroke.library_management_system.artist.enums.ReservationStatus;

import java.util.Date;

public class BookReservation {
    private String itemId;
    private Date created;
    private ReservationStatus status;

    private int memberId;

    public BookReservation(String itemId, Date created, ReservationStatus status, int memberId) {
        this.itemId = itemId;
        this.created = created;
        this.status = status;
        this.memberId = memberId;
    }

    public BookReservation getStatus(){
        return  this;
    }

    public BookReservation fetchReservationDetails(BookItems bookItems){
     return this;
    }
}
