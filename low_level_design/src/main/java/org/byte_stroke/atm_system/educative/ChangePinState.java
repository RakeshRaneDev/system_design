package org.byte_stroke.atm_system.educative;

public class ChangePinState extends ATMState{
    @Override
    public void pinChange(ATM atm, AtmCard card, int newPin){
        card.setPin(newPin);
        atm.getPrinter().printReceipt("Pin changed successfully.");
        atm.getScreen().showMessage("Pin Chnaged");
        atm.setAtmStatus(ATMStatus.SELECTION_OPTION);
        atm.setCurrentAtmState(new SelectionOptionState());
    }
}
