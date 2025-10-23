package org.byte_stroke.atm_system.educative;

import java.util.StringJoiner;

public class User {
    private AtmCard card;
    private BankAccount bankAccount;

    public User(AtmCard card, BankAccount bankAccount) {
        this.card = card;
        this.bankAccount = bankAccount;
    }

    public AtmCard getCard() {
        return card;
    }

    public void setCard(AtmCard card) {
        this.card = card;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("card=" + card)
                .add("bankAccount=" + bankAccount)
                .toString();
    }
}
