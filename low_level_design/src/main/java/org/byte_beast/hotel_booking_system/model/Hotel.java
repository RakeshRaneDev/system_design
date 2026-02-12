package org.byte_beast.hotel_booking_system.model;

import java.util.List;

public class Hotel {
    private String name;
    private String id;
    private Address address;
    private List<Room> rooms;
    private Person contact;

    public Hotel(String name, String id, Address address, List<Room> rooms, Person contact) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.rooms = rooms;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
