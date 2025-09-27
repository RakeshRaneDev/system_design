package org.binary_brush_stroke.behavioural.strategy.payment;

public class UpiPayment implements Payment{
    @Override
    public void processPayment() {
        System.out.println("Upi Payment in progress");
    }
}
