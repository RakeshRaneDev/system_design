package org.byte_beast.inventory_management_system;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class WareHouse {
    private final String wareHoseId;

    private final String name;
    private final String contactInfo;
    private final String location;
    private final String address;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public WareHouse(String wareHoseId, String name, String contactInfo, String location, String address) {
        this.wareHoseId = wareHoseId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.location = location;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public String getWareHoseId() {
        return wareHoseId;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WareHouse wareHouse = (WareHouse) o;
        return Objects.equals(wareHoseId, wareHouse.wareHoseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wareHoseId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WareHouse.class.getSimpleName() + "[", "]")
                .add("wareHoseId='" + wareHoseId + "'")
                .add("name='" + name + "'")
                .add("contactInfo='" + contactInfo + "'")
                .add("location='" + location + "'")
                .add("address='" + address + "'")
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .toString();
    }
}
