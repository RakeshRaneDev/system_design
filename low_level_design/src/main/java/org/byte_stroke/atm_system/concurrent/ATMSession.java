package org.byte_stroke.atm_system.concurrent;

import org.byte_stroke.atm_system.educative.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Represents a user session in the ATM system with thread-safe operations
 */
public class ATMSession {
    
    private final String sessionId;
    private final User user;
    private final LocalDateTime startTime;
    private final Lock sessionLock = new ReentrantLock();
    
    // Session state
    private volatile ATMState currentState;
    private volatile boolean authenticated = false;
    private volatile AtmCard insertedCard = null;
    private volatile boolean sessionActive = true;
    
    // Transaction tracking
    private int transactionCount = 0;
    private final int maxTransactionsPerSession = 10;
    
    public ATMSession(String sessionId, User user) {
        this.sessionId = sessionId;
        this.user = user;
        this.startTime = LocalDateTime.now();
        this.currentState = new IdleState();
    }
    
    /**
     * Thread-safe card insertion
     */
    public boolean insertCard(AtmCard card) {
        sessionLock.lock();
        try {
            if (!sessionActive || currentState == null) {
                return false;
            }
            
            if (currentState instanceof IdleState) {
                ConcurrentATM atm = ConcurrentATM.getInstance();
                if (atm.readCard(card)) {
                    this.insertedCard = card;
                    this.currentState = new HasCardState();
                    return true;
                }
            }
            return false;
        } finally {
            sessionLock.unlock();
        }
    }
    
    /**
     * Thread-safe PIN authentication
     */
    public boolean authenticatePin(int pin) {
        sessionLock.lock();
        try {
            if (!sessionActive || insertedCard == null) {
                return false;
            }
            
            if (currentState instanceof HasCardState) {
                if (insertedCard.validateCard(pin)) {
                    this.authenticated = true;
                    this.currentState = new SelectionOptionState();
                    return true;
                }
            }
            return false;
        } finally {
            sessionLock.unlock();
        }
    }
    
    /**
     * Process transaction with proper state management
     */
    public TransactionResult processTransaction(TransactionType type, Object... params) {
        sessionLock.lock();
        try {
            if (!sessionActive || !authenticated) {
                return new TransactionResult(false, "Session not authenticated or inactive");
            }
            
            if (transactionCount >= maxTransactionsPerSession) {
                return new TransactionResult(false, "Maximum transactions per session exceeded");
            }
            
            // Check session timeout (30 minutes)
            if (Duration.between(startTime, LocalDateTime.now()).toMinutes() > 30) {
                sessionActive = false;
                return new TransactionResult(false, "Session timeout");
            }
            
            TransactionResult result = null;
            
            switch (type) {
                case BALANCE_INQUIRY:
                    result = processBalanceInquiry();
                    break;
                case CASH_WITH_DRAW:
                    if (params.length > 0 && params[0] instanceof Double) {
                        result = processCashWithdrawal((Double) params[0]);
                    } else {
                        result = new TransactionResult(false, "Invalid withdrawal amount");
                    }
                    break;
                case FUNDS_TRANSFER:
                    if (params.length >= 2 && params[0] instanceof BankAccount && params[1] instanceof Double) {
                        result = processFundsTransfer((BankAccount) params[0], (Double) params[1]);
                    } else {
                        result = new TransactionResult(false, "Invalid transfer parameters");
                    }
                    break;
                case CHANGE_PIN:
                    if (params.length > 0 && params[0] instanceof Integer) {
                        result = processPinChange((Integer) params[0]);
                    } else {
                        result = new TransactionResult(false, "Invalid PIN");
                    }
                    break;
                default:
                    result = new TransactionResult(false, "Unsupported transaction type");
            }
            
            if (result.isSuccess()) {
                transactionCount++;
            }
            
            return result;
        } finally {
            sessionLock.unlock();
        }
    }
    
    /**
     * Process balance inquiry
     */
    private TransactionResult processBalanceInquiry() {
        try {
            BankAccount account = user.getBankAccount();
            double balance = account.getTotalBalance();
            
            // Update state to balance inquiry
            this.currentState = new BalanceInquiryState();
            
            return new TransactionResult(true, "Balance: $" + balance);
        } catch (Exception e) {
            return new TransactionResult(false, "Error retrieving balance: " + e.getMessage());
        }
    }
    
    /**
     * Process cash withdrawal with proper validation
     */
    private TransactionResult processCashWithdrawal(double amount) {
        try {
            BankAccount account = user.getBankAccount();
            ConcurrentATM atm = ConcurrentATM.getInstance();
            
            // Validate withdrawal amount
            if (amount <= 0) {
                return new TransactionResult(false, "Invalid withdrawal amount");
            }
            
            if (amount > account.getWithDrawLimit()) {
                return new TransactionResult(false, "Amount exceeds withdrawal limit");
            }
            
            if (amount > account.getTotalBalance()) {
                return new TransactionResult(false, "Insufficient account balance");
            }
            
            if (amount > atm.getAtmBalance()) {
                return new TransactionResult(false, "ATM has insufficient cash");
            }
            
            // Perform withdrawal
            if (account.withDraw(amount) && atm.withdrawCash(amount)) {
                this.currentState = new CashWithDrawState();
                return new TransactionResult(true, "Withdrawal successful. Amount: $" + amount);
            } else {
                return new TransactionResult(false, "Withdrawal failed");
            }
        } catch (Exception e) {
            return new TransactionResult(false, "Withdrawal error: " + e.getMessage());
        }
    }
    
    /**
     * Process funds transfer
     */
    private TransactionResult processFundsTransfer(BankAccount toAccount, double amount) {
        try {
            BankAccount fromAccount = user.getBankAccount();
            
            if (amount <= 0) {
                return new TransactionResult(false, "Invalid transfer amount");
            }
            
            if (amount > fromAccount.getTotalBalance()) {
                return new TransactionResult(false, "Insufficient balance for transfer");
            }
            
            if (fromAccount.transfer(toAccount, amount)) {
                this.currentState = new TransferMoneyState();
                return new TransactionResult(true, "Transfer successful. Amount: $" + amount);
            } else {
                return new TransactionResult(false, "Transfer failed");
            }
        } catch (Exception e) {
            return new TransactionResult(false, "Transfer error: " + e.getMessage());
        }
    }
    
    /**
     * Process PIN change
     */
    private TransactionResult processPinChange(int newPin) {
        try {
            if (insertedCard != null) {
                // In a real system, this would update the PIN in the database
                this.currentState = new ChangePinState();
                return new TransactionResult(true, "PIN change successful");
            } else {
                return new TransactionResult(false, "No card inserted");
            }
        } catch (Exception e) {
            return new TransactionResult(false, "PIN change error: " + e.getMessage());
        }
    }
    
    /**
     * Return card and end session
     */
    public boolean returnCard() {
        sessionLock.lock();
        try {
            if (insertedCard != null) {
                this.insertedCard = null;
                this.authenticated = false;
                this.currentState = new IdleState();
                return true;
            }
            return false;
        } finally {
            sessionLock.unlock();
        }
    }
    
    /**
     * End session
     */
    public void endSession() {
        sessionLock.lock();
        try {
            this.sessionActive = false;
            this.authenticated = false;
            this.insertedCard = null;
            this.currentState = null;
        } finally {
            sessionLock.unlock();
        }
    }
    
    /**
     * Cleanup session resources
     */
    public void cleanup() {
        endSession();
    }
    
    // Getters
    public String getSessionId() {
        return sessionId;
    }
    
    public User getUser() {
        return user;
    }
    
    public boolean isAuthenticated() {
        return authenticated;
    }
    
    public boolean isSessionActive() {
        return sessionActive;
    }
    
    public ATMState getCurrentState() {
        return currentState;
    }
    
    public AtmCard getInsertedCard() {
        return insertedCard;
    }
    
    public int getTransactionCount() {
        return transactionCount;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public long getSessionDurationMinutes() {
        return Duration.between(startTime, LocalDateTime.now()).toMinutes();
    }
}
