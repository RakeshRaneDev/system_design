package org.byte_stroke.parking_lot.entity;

import org.byte_stroke.parking_lot.enums.TicketStatus;
import org.byte_stroke.parking_lot.model.Payment;
import org.byte_stroke.parking_lot.model.Vehicle;

import java.util.Date;

public class ParkingTicket {
    private static int ticketSeed = 1000;
    private int ticketNo;
    private int slotNo;
    private Vehicle vehicle;

    private Date entryTime;
    private Date existTime;
    private double amount;
    private TicketStatus ticketStatus;
    private Payment payment;

    public  ParkingTicket(int slotNo, Vehicle vehicle){
        this.ticketNo = ticketSeed++;
        this.slotNo = slotNo;
        this.vehicle = vehicle;
        this.entryTime = new Date();
        this.ticketStatus = TicketStatus.ISSUE;
        vehicle.AssignTicket(this);
        System.out.println("Ticket issued "+ ticketNo);
    }


    public int getTicketNo() {
        return ticketNo;
    }


    public int getSlotNo() {
        return slotNo;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }



    public Date getEntryTime() {
        return entryTime;
    }


    public Date getExistTime() {
        return existTime;
    }

    public void setExistTime(Date existTime) {
        this.existTime = existTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

}
