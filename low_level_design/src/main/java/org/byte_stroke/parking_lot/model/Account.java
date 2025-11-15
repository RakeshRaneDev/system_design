package org.byte_stroke.parking_lot.model;

import org.byte_stroke.library_management_system.artist.Entity.Person;
import org.byte_stroke.parking_lot.enums.AccountType;

public abstract class Account {
    private String userName;
    private String password;
    private Person person;

    private AccountType accountType;

    public  abstract  boolean resetPassword();
}
