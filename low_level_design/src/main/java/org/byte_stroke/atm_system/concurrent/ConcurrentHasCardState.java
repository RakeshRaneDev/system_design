package org.byte_stroke.atm_system.concurrent;

import org.byte_stroke.atm_system.educative.*;

/**
 * Thread-safe HasCardState implementation
 */
public class ConcurrentHasCardState extends ConcurrentATMState {
    
    @Override
    protected boolean performInsertCard(ATMSession session, AtmCard card) {
        // Card already inserted
        return false;
    }
    
    @Override
    protected boolean performAuthenticatePin(ATMSession session, int pin) {
        return session.authenticatePin(pin);
    }
    
    @Override
    protected TransactionResult performTransaction(ATMSession session, TransactionType type, Object... params) {
        return new TransactionResult(false, "Please authenticate PIN first.");
    }
    
    @Override
    protected boolean performReturnCard(ATMSession session) {
        return session.returnCard();
    }
    
    @Override
    protected void performEndSession(ATMSession session) {
        session.returnCard();
        session.endSession();
    }
}
