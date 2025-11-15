package org.byte_stroke.parking_lot;

import org.byte_stroke.parking_lot.entity.ParkingTicket;
import org.byte_stroke.parking_lot.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private static ParkingLot instance = null;

    public ParkingRate parkingRate = new ParkingRate();
    private Map<Integer, ParkingSpot> spots = new HashMap<>();
    private Map<Integer, ParkingTicket> tickets = new HashMap<>();
    private  Map<Integer, DisplayBoard> boards = new HashMap<>();

    private ParkingLot(){

    }
    public  static ParkingLot getInstance(){
        if(instance ==null){
            instance = new ParkingLot();
        }
        return instance;
    }

    public void addSpot(ParkingSpot spot){
        spots.put(spot.getId(), spot);
    }

    public void addBoard(DisplayBoard board){
        boards.put(board.getId(), board);
    }

    public ParkingSpot getSpot(int id){
        return spots.get(id);
    }
    public void freeSpot(int id){
        ParkingSpot spot = spots.get(id);
        if(spot!=null){
            spot.removeVehicle();
        }
    }

    public Collection<ParkingSpot> getAllSpot(){
        return spots.values();
    }

    public ParkingTicket parkVehicle(Vehicle v){
        for(ParkingSpot spot: spots.values()){
            if(spot.isSpotFree() && canFit(spot, v)){
                spot.assignVehicle(v);
                ParkingTicket ticket = new ParkingTicket(spot.getId(), v);
                tickets.put(ticket.getTicketNo(), ticket);
                return ticket;

            }
        }
        return null;
    }

    private boolean canFit(ParkingSpot spot,  Vehicle v){
        if(v instanceof MotorCycle && spot instanceof MotorCycleSlot){
            return true;
        }
        if((v instanceof Truck || v instanceof  Van) && spot instanceof Large){
            return true;
        }

        if(v instanceof Car && (spot instanceof Compact ||spot instanceof Handicapped )){
            return true;
        }
        return false;
    }

}
