package org.byte_stroke.inventory_management_system;

import java.util.Objects;

public class Location {
    private final String locationId; // immutable identifier
    private String name;
    private String address;

    public Location(String locationId, String name, String address) {
        if (locationId == null || locationId.isBlank()) {
            throw new IllegalArgumentException("locationId must be non-empty");
        }
        this.locationId = locationId;
        this.name = name;
        this.address = address;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId.equals(location.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId='" + locationId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
