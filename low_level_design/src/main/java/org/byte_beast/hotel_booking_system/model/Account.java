package org.byte_beast.hotel_booking_system.model;

import  org.byte_beast.hotel_booking_system.enums.AccountStatus;


public class Account {
    private String id;
    private String password;
    private AccountStatus accountStatus;

    public Account(String id, String password) {
        this.id = id;
        this.password = password;
        this.accountStatus = AccountStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }


    public String getPassword() {
        return password;
    }

    public void restPassword(String password) {
        this.password = password;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
