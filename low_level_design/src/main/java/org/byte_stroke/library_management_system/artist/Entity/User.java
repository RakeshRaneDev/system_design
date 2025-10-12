package org.byte_stroke.library_management_system.artist.Entity;

import org.byte_stroke.library_management_system.artist.enums.AccountStatus;

public abstract class User {

    int id;
    String password;
    AccountStatus status;
    Person person;

    public User(int id, String password, AccountStatus status, Person person) {
        this.id = id;
        this.password = password;
        this.status = status;
        this.person = person;
    }

    public void resetPassword(String password){

    }
}
