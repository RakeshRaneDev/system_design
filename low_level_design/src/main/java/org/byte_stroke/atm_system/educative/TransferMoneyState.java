package org.byte_stroke.atm_system.educative;

public class TransferMoneyState extends ATMState{
    @Override
    public void transferMoney(ATM atm, AtmCard card, BankAccount toAccount, double amount){
        BankAccount fromAccount = atm.getActiveUser().getBankAccount();
        double accLimit = fromAccount.getWithDrawLimit();
        if(amount<= fromAccount.getAvailableBalance() & amount<= accLimit){
            boolean transferred = fromAccount.transfer(toAccount, amount);
            if(transferred){
                atm.getPrinter().printReceipt(" Transferred : " + amount
                        + " from account: "+ fromAccount.getAccountNumber()+ " to Acc: " + toAccount.getAccountNumber()+
                        " Your balance:  " + fromAccount.getAvailableBalance());
                atm.getScreen().showMessage("Transfer successful!");
            }else {
                atm.getScreen().showMessage("Transfer failed");
            }

        }else{
            atm.getScreen().showMessage("Transfer denied. Check your balance or limits");
        }
        atm.setAtmStatus(ATMStatus.SELECTION_OPTION);
        atm.setCurrentAtmState(new SelectionOptionState());

    }
}
