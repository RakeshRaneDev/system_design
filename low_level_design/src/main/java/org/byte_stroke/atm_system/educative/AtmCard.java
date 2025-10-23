package org.byte_stroke.atm_system.educative;

import java.util.Date;
import java.util.StringJoiner;

public class AtmCard {
    private String name;
    private String cardNumber;
    private int pin;
    private String expiredDate;

    public AtmCard(){

    }

    public AtmCard(String name, String cardNumber, int pin, String expiredDate) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.expiredDate = expiredDate;
    }

    public boolean validateCard(int pin){
        return this.pin==pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AtmCard.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("cardNumber='" + cardNumber + "'")
                .add("pin=" + pin)
                .add("expiredDate=" + expiredDate)
                .toString();
    }
}
