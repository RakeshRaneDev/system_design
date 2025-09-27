package org.binary_brush_stroke.behavioural.strategy.payment;

public class CardPayment implements Payment{
    @Override
    public void processPayment() {
        System.out.println("card Payment in process");
    }
}
