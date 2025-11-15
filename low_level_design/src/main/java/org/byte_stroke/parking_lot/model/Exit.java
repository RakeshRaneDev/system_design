package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.ParkingLot;
import org.byte_stroke.parking_lot.entity.ParkingTicket;
import org.byte_stroke.parking_lot.enums.TicketStatus;

import java.util.Date;

public class Exit {
    int id ;
    public  Exit(int id){
        this.id = id;
    }

    public void validateTicket(ParkingTicket t){
        Date now = new Date();
        double hours = (now.getTime() - t.getEntryTime().getTime())/3600000.0;
        double fee = ParkingLot.getInstance().parkingRate.calculate(hours,
                t.getVehicle(), ParkingLot.getInstance().getSpot(t.getSlotNo()));
        t.setAmount(fee);
        System.out.println(String.
                format("Ticket: %d | parked: 0.2%f hours | fee: r%.2f", t.getTicketNo(), hours, fee));
        Payment payment = (fee>10)? new CreditCard(fee): new Cash(fee);
        payment.initiatePayment();
        ParkingLot.getInstance().freeSpot(t.getSlotNo());
        t.setTicketStatus(TicketStatus.PAID);
    }

}
