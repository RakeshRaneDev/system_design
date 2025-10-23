package org.byte_stroke.atm_system.educative;

public class IdleState extends ATMState{
    @Override
    public void insertCard(ATM atm, AtmCard card){
        if(atm.getCardReader().readCard(card)){
            atm.setInsertedCard(card);
            atm.setAtmStatus(ATMStatus.HAS_CARD);
            atm.setCurrentAtmState(new HasCardState());
            atm.getScreen().showMessage("Please insert your pin");
        }else{
            atm.getScreen().showMessage("Card reading failed.");

        }
    }

}
