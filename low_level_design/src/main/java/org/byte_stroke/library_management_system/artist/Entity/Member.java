package org.byte_stroke.library_management_system.artist.Entity;

import org.byte_stroke.library_management_system.artist.enums.AccountStatus;

import java.util.Date;
import java.util.List;

public class Member extends User{
    private Date memberShipStartDate;
    private List<BookItems> listOfCheckoutBooks;


    public Member(int id, String password, AccountStatus status, Person person, Date memberShipStartDate) {
        super(id, password, status, person);
        this.memberShipStartDate = memberShipStartDate;
    }

    public int getCheckoutBook(){
        return this.listOfCheckoutBooks.size();
    }

    public boolean reservedBookItem(BookItems bookItems ){
return  false;
    }
    public void incrementTotalBooks(){

    }

    public void checkoutBookItem(BookItems bookItems){

    }

    public void returnBookItem(BookItems bookItems){

    }

    public  boolean renewBookItem(BookItems bookItems){
return false;
    }

    public int checkForFine(BookItems bookItems){
return 0;
    }




}
