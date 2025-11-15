package org.byte_stroke.parking_lot.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DisplayBoard {
    private int id;
    private Map<String, Integer> freeCount = new HashMap<>();

    public DisplayBoard (int id){
        this.id = id;
    }

    public void update(Collection<ParkingSpot> spots){
        freeCount.clear();
        for (ParkingSpot spot: spots){
            if(spot.isFree){
                String type = spot.getClass().getSimpleName();
                freeCount.put(type, freeCount.getOrDefault(type,0)+1);
            }
        }

    }
    public void showFreeSlot(){
        System.out.println("\n Free slot by type");
        for(Map.Entry<String, Integer> entry : freeCount.entrySet()){
            System.out.println(String.format("%s: %d", entry.getKey(), entry.getValue()));
        }

    }
    public  int getId(){
        return this.id;
    }
}
