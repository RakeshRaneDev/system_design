package org.binary_brush_stroke.behavioural.strategy.payment;

public class PaymentStrategy {
    private Payment payment;
    PaymentStrategy(Payment payment){
        this.payment = payment;
    }

    public  void processPayment(){
        payment.processPayment();
    }
}
