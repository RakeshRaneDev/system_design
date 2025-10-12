package org.byte_stroke.library_management_system.artist.Entity;

import java.util.Date;

public class LibraryCard {
    private  int cardNumber;
    private boolean active;
    private Date issue;

    public LibraryCard(int cardNumber, boolean active, Date issue) {
        this.cardNumber = cardNumber;
        this.active = active;
        this.issue = issue;
    }

    public boolean isActive(){
 return this.active;
    }
}
