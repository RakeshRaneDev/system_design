package org.byte_stroke.atm_system.educative;

public class SavingAccount extends BankAccount{

    public  SavingAccount(int accountNumber, double balance){
        super(accountNumber, balance);
    }


    @Override
    public double getWithDrawLimit() {
        return 1000.0;
    }
}
