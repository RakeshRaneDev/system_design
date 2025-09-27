package org.binary_brush_stroke.behavioural.strategy.payment;

public class Main {
    public static void main(String[] args) {
        PaymentStrategy strategy = new PaymentStrategy(new CardPayment());
        strategy.processPayment();
    }
}
