package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.enums.PaymentStatus;

public class Cash extends Payment{

    public Cash(double amount) {
        super(amount);
    }

    @Override
    public boolean initiatePayment() {
        status = PaymentStatus.COMPLETED;
        System.out.println("Cash payment of rs "+ amount+ " completed.");
        return true;
    }
}
