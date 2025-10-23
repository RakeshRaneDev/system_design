package org.byte_stroke.atm_system.educative;

public abstract class ATMState {
    public void insertCard(ATM atm, AtmCard card){}

    public void authenticatePin(ATM atm, AtmCard card , int pin){}

    public void selectOption(ATM atm, TransactionType type){}

    public void cashWithDraw(ATM atm, AtmCard card, double amount){}

    public void displayBalance(ATM atm, AtmCard card){}

    public void transferMoney(ATM atm, AtmCard card, BankAccount toAccount, double amount){}

    public void pinChange(ATM atm, AtmCard card, int newPin){}
    public  void cancelTransfer(ATM atm){}

    public void returnCard(ATM atm){}

    public  void exit(ATM atm){}


}
