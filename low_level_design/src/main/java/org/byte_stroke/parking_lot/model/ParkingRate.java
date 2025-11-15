package org.byte_stroke.parking_lot.model;

public class ParkingRate {
    public double calculate(double hours, Vehicle vehicle,  ParkingSpot spot){
        int hrs = (int)Math.ceil(hours);
        double fee = 0;
        if(hrs>=1) fee+= 4;
        if(hrs>=2) fee+= 3.5;
        if(hrs>=3) fee+= 3.5;
        if(hrs>3) fee+= (hrs-3)*2.5;
        return fee;
    }
}
