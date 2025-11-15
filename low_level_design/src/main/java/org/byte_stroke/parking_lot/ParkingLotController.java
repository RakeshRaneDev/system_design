package org.byte_stroke.parking_lot;

import org.byte_stroke.parking_lot.entity.ParkingTicket;
import org.byte_stroke.parking_lot.model.*;

public class ParkingLotController {
    public static void main(String[] args) throws InterruptedException {
        ParkingLot lot = ParkingLot.getInstance();
        // adding the spot
        lot.addSpot(new Handicapped(1));
        lot.addSpot(new Compact(2));
        lot.addSpot(new Large(3));
        lot.addSpot(new MotorCycleSlot(4));

        DisplayBoard board = new DisplayBoard(1);
        lot.addBoard(board);

        Entrance entrance = new Entrance(1);

        Exit exit = new Exit(1);
        //----------Scenaro 1: Custemers enters and park-------------
        System.out.println("\n-Scenaro 1: Custemers enters and park \n");
        Vehicle car = new Car("MH-07-AU-3456");
        ParkingTicket ticket = entrance.getTicket(car);

        System.out.println(" updating dispay board after parking");
        board.update(lot.getAllSpot());
        board.showFreeSlot();

        // ------2 Cutomer exist and pay
        System.out.println("\n scenario:2 customer exist and pay");
        Thread.sleep(1500);
        exit.validateTicket(ticket);
        board.update(lot.getAllSpot());
        board.showFreeSlot();



    }
}
