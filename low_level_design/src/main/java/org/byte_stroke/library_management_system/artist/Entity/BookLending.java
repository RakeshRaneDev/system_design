package org.byte_stroke.library_management_system.artist.Entity;

import java.util.Date;

public class BookLending {
    private String itemId;
    private int memberId;
    private Date created;
    private Date returnDate;
    private Date dueDate;

    public void lendBook(BookItems bookItems){

    }

    public BookLending fetchLendingDetails(BookItems bookItems){
return this;
    }

    private  Date getReturnDate(){
        return this.returnDate;
    }


}
