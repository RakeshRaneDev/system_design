package org.byte_stroke.inventory_management_system;

import java.util.*;

public class LocationRepository {
    private final Map<String, Location> locationIdToLocation = new HashMap<>();

    public Optional<Location> findById(String locationId) {
        return Optional.ofNullable(locationIdToLocation.get(locationId));
    }

    public List<Location> findAll() {
        return new ArrayList<>(locationIdToLocation.values());
    }

    public void save(Location location) {
        locationIdToLocation.put(location.getLocationId(), location);
    }

    public boolean delete(String locationId) {
        return locationIdToLocation.remove(locationId) != null;
    }
}
