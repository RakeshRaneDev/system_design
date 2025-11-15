package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.enums.PaymentStatus;

public class CreditCard extends Payment{

    public CreditCard(double amount) {
        super(amount);
    }

    @Override
    public boolean initiatePayment() {
        status = PaymentStatus.COMPLETED;
        System.out.println("Credit card payment of rs "+ amount+ " completed.");
        return true;
    }
}
