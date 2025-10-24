package org.byte_stroke.atm_system.concurrent;

import org.byte_stroke.atm_system.educative.*;

/**
 * Thread-safe SelectionOptionState implementation
 */
public class ConcurrentSelectionOptionState extends ConcurrentATMState {
    
    @Override
    protected boolean performInsertCard(ATMSession session, AtmCard card) {
        // Card already inserted and authenticated
        return false;
    }
    
    @Override
    protected boolean performAuthenticatePin(ATMSession session, int pin) {
        // Already authenticated
        return true;
    }
    
    @Override
    protected TransactionResult performTransaction(ATMSession session, TransactionType type, Object... params) {
        return session.processTransaction(type, params);
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
