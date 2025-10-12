package org.byte_stroke.library_management_system.artist.Entity;

public class Rack {
    private int number;
    private String identifier;

    public Rack(int number, String identifier) {
        this.number = number;
        this.identifier = identifier;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
