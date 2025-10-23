package org.byte_stroke.atm_system.educative;

public class CashWithDrawState extends ATMState{
    @Override
    public void cashWithDraw(ATM atm, AtmCard card, double amount){
        BankAccount account = atm.getActiveUser().getBankAccount();
        double accLimit = account.getWithDrawLimit();
        if(amount<= account.getAvailableBalance() && amount<= accLimit && amount<= atm.getAtmBalance()){

            boolean dispensed = atm.getCashDispenser().dispenseCash((int)amount, atm);
            if(dispensed){
                account.withDraw(amount);
                atm.getPrinter().printReceipt("With draw: " + amount+ " Remaining balance:  " + account.getAvailableBalance());
                atm.getScreen().showMessage("Please collect your cash");

            }else{
                atm.getScreen().showMessage("Unable to dispensed requested amount");
            }


        }else{
            atm.getScreen().showMessage("Withdrawal denied. Check your balance or limit");
        }
        atm.setAtmStatus(ATMStatus.SELECTION_OPTION);
        atm.setCurrentAtmState(new SelectionOptionState());
    }
}
