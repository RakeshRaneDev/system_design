package org.byte_stroke.atm_system.educative;

public abstract class BankAccount {
    protected  int accountNumber;
    protected  double availableBalance;

    public BankAccount(int accountNumber, double balance){
        this.accountNumber = accountNumber;
        this.availableBalance = balance;
    }

    public  double getTotalBalance(){
        return availableBalance;
    }


    public boolean withDraw(double amount){
        if(amount<=availableBalance){
            availableBalance-=amount;
            return true;
        }
        return false;
    }

    public boolean transfer(BankAccount toAccount, double amount){
        if(amount<= availableBalance){
            availableBalance-= amount;
            toAccount.availableBalance  += amount;
            return  true;

        }
        return false;
    }

    public abstract double getWithDrawLimit();

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }
}
