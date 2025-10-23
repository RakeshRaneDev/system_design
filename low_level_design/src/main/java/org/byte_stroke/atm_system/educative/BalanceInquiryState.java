package org.byte_stroke.atm_system.educative;

public class BalanceInquiryState extends ATMState{
@Override
    public void displayBalance(ATM atm, AtmCard card){
       atm.getScreen().showMessage("your current balance is : "+
               atm.getActiveUser().getBankAccount().getAvailableBalance());

       atm.getPrinter().printReceipt("Balance: " +
               atm.getActiveUser().getBankAccount().getAvailableBalance());

       atm.setAtmStatus(ATMStatus.SELECTION_OPTION);
       atm.setCurrentAtmState(new SelectionOptionState());
  }
}
