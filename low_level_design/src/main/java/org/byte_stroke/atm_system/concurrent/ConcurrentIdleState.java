package org.byte_stroke.atm_system.concurrent;

import org.byte_stroke.atm_system.educative.*;

/**
 * Thread-safe IdleState implementation
 */
public class ConcurrentIdleState extends ConcurrentATMState {
    
    @Override
    protected boolean performInsertCard(ATMSession session, AtmCard card) {
        if (session.insertCard(card)) {
            // State transition handled by session
            return true;
        }
        return false;
    }
    
    @Override
    protected boolean performAuthenticatePin(ATMSession session, int pin) {
        // Cannot authenticate in idle state
        return false;
    }
    
    @Override
    protected TransactionResult performTransaction(ATMSession session, TransactionType type, Object... params) {
        return new TransactionResult(false, "No card inserted. Please insert card first.");
    }
    
    @Override
    protected boolean performReturnCard(ATMSession session) {
        // No card to return in idle state
        return false;
    }
    
    @Override
    protected void performEndSession(ATMSession session) {
        // Clean up idle state
        session.endSession();
    }
}
