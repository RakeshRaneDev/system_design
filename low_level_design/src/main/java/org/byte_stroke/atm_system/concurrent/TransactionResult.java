package org.byte_stroke.atm_system.concurrent;

/**
 * Represents the result of a transaction operation
 */
public class TransactionResult {
    
    private final boolean success;
    private final String message;
    private final long timestamp;
    private final String transactionId;
    
    public TransactionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.transactionId = generateTransactionId();
    }
    
    public TransactionResult(boolean success, String message, String transactionId) {
        this.success = success;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.transactionId = transactionId;
    }
    
    private String generateTransactionId() {
        return "TXN_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    @Override
    public String toString() {
        return String.format("TransactionResult{success=%s, message='%s', transactionId='%s', timestamp=%d}", 
                           success, message, transactionId, timestamp);
    }
}
