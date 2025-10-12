package org.byte_stroke.library_management_system.artist.Entity;

import org.byte_stroke.library_management_system.artist.enums.UserStatus;

public abstract class Person {
    String name;
    Address address;
    String email;
    String phone;

    public Person(String name, Address address, String email, String phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }
}
