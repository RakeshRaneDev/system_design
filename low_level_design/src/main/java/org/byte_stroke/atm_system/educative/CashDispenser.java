package org.byte_stroke.atm_system.educative;

public class CashDispenser {

    public boolean dispenseCash(int amount, ATM atm){
        if( amount>=0 && amount<= atm.getAtmBalance()){
            int left = amount;
            int hundred = Math.min(left/100, atm.getNumberOfFiftyDollarsBills());
            left -= hundred*100;

            int fifties = Math.min(left/50, atm.getNumberOfFiftyDollarsBills());
            left-= fifties*50;

            int ten = Math.min(left/10, atm.getNumberOfTenDollarsBills());
            left-= fifties*50;

            if(left==0){
                atm.setNumberOfHundredDollarsBills(atm.getNumberOfHundredDollarsBills() - hundred);
                atm.setNumberOfFiftyDollarsBills(atm.getNumberOfFiftyDollarsBills() - fifties);
                atm.setNumberOfTenDollarsBills(atm.getNumberOfTenDollarsBills() - ten);
                System.out.println("[ATM] Dispensing: "+ amount + "as: "+
                        hundred + "x100, " + fifties+ "x50, "+ ten+"x10.");
                return true;

            }
        }
        System.out.println("[ATM] unable to dispense requested amount");
        return false;
    }
}
