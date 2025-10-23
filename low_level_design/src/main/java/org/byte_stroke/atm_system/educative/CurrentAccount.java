package org.byte_stroke.atm_system.educative;

public class CurrentAccount extends  BankAccount{
    public CurrentAccount(int accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public double getWithDrawLimit() {
        return 5000.0;
    }
}
