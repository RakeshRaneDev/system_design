package org.byte_stroke.atm_system.concurrent;

import org.byte_stroke.atm_system.educative.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

/**
 * Thread-safe abstract base class for ATM states
 */
public abstract class ConcurrentATMState {
    
    protected final Lock stateLock = new ReentrantLock();
    protected volatile boolean stateActive = true;
    
    /**
     * Thread-safe card insertion
     */
    public boolean insertCard(ATMSession session, AtmCard card) {
        stateLock.lock();
        try {
            if (!stateActive) {
                return false;
            }
            return performInsertCard(session, card);
        } finally {
            stateLock.unlock();
        }
    }
    
    /**
     * Thread-safe PIN authentication
     */
    public boolean authenticatePin(ATMSession session, int pin) {
        stateLock.lock();
        try {
            if (!stateActive) {
                return false;
            }
            return performAuthenticatePin(session, pin);
        } finally {
            stateLock.unlock();
        }
    }
    
    /**
     * Thread-safe transaction processing
     */
    public TransactionResult processTransaction(ATMSession session, TransactionType type, Object... params) {
        stateLock.lock();
        try {
            if (!stateActive) {
                return new TransactionResult(false, "State not active");
            }
            return performTransaction(session, type, params);
        } finally {
            stateLock.unlock();
        }
    }
    
    /**
     * Thread-safe card return
     */
    public boolean returnCard(ATMSession session) {
        stateLock.lock();
        try {
            if (!stateActive) {
                return false;
            }
            return performReturnCard(session);
        } finally {
            stateLock.unlock();
        }
    }
    
    /**
     * Thread-safe session end
     */
    public void endSession(ATMSession session) {
        stateLock.lock();
        try {
            stateActive = false;
            performEndSession(session);
        } finally {
            stateLock.unlock();
        }
    }
    
    // Abstract methods to be implemented by concrete states
    protected abstract boolean performInsertCard(ATMSession session, AtmCard card);
    protected abstract boolean performAuthenticatePin(ATMSession session, int pin);
    protected abstract TransactionResult performTransaction(ATMSession session, TransactionType type, Object... params);
    protected abstract boolean performReturnCard(ATMSession session);
    protected abstract void performEndSession(ATMSession session);
    
    public boolean isStateActive() {
        return stateActive;
    }
}
