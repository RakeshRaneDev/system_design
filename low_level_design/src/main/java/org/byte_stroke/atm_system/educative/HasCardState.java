package org.byte_stroke.atm_system.educative;

public class HasCardState extends  ATMState{


    @Override
    public void authenticatePin(ATM atm, AtmCard card , int pin){
        if(card.validateCard(pin)){
            atm.setAuthenticated(true);
            atm.setAtmStatus(ATMStatus.SELECTION_OPTION);
            atm.setCurrentAtmState(new SelectionOptionState());
            atm.getScreen().showMessage("PIN verified. Please select the transaction");
        }else{
            atm.getScreen().showMessage("Incorrect pin. Please try again or cancel");
        }
    }

    @Override
    public void returnCard(ATM atm){
        atm.setInsertedCard(null);
        atm.setAtmStatus(ATMStatus.IDLE);
        atm.setAuthenticated(false);
        atm.setCurrentAtmState(new IdleState());
        atm.getScreen().showMessage("Card return. Thank You!");
    }
}
