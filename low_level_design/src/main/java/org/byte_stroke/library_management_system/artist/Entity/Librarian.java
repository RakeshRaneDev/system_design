package org.byte_stroke.library_management_system.artist.Entity;

import org.byte_stroke.library_management_system.artist.enums.AccountStatus;
import org.byte_stroke.library_management_system.artist.enums.BookType;

public class Librarian extends User{
    public Librarian(int id, String password, AccountStatus status, Person person) {
        super(id, password, status, person);
    }

    public  boolean addBookItem(BookItems bookItems){
return false;
    }

    public void blockMember(Member member){

    }

    public  void unblockMember(Member member){

    }
}
