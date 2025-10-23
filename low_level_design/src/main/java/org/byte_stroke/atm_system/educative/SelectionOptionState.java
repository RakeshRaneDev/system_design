package org.byte_stroke.atm_system.educative;

public class SelectionOptionState extends  ATMState{
    @Override
    public void selectOption(ATM atm, TransactionType type){
        switch (type){
            case BALANCE_INQUIRY:
                atm.setAtmStatus(ATMStatus.BALANCE_INQUIRY);
                atm.setCurrentAtmState(new BalanceInquiryState());
                break;

            case CASH_WITH_DRAW:
                atm.setAtmStatus(ATMStatus.WITH_DRAW);
                atm.setCurrentAtmState(new CashWithDrawState());
                break;

            case FUNDS_TRANSFER:
                atm.setAtmStatus(ATMStatus.TRANSFER_MONEY);
                atm.setCurrentAtmState(new TransferMoneyState());
                break;

            case CHANGE_PIN:
                atm.setAtmStatus(ATMStatus.CHANGE_PIN);
                atm.setCurrentAtmState(new ChangePinState());
                break;

            case CANCEL:
                atm.getCurrentAtmState().returnCard(atm);
                break;

        }
    }
}
