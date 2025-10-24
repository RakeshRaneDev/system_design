package org.byte_stroke.atm_system.concurrent;

import org.byte_stroke.atm_system.educative.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe concurrent ATM implementation that supports multiple users
 * and handles concurrent transactions safely.
 */
public class ConcurrentATM {
    
    // Thread-safe singleton implementation
    private static volatile ConcurrentATM instance;
    private static final Object instanceLock = new Object();
    
    // Core ATM components with thread safety
    private final CardReader cardReader;
    private final KeyPad keyPad;
    private final Screen screen;
    private final Printer printer;
    private final CashDispenser cashDispenser;
    
    // Thread-safe cash management
    private final AtomicInteger atmBalance = new AtomicInteger(0);
    private final AtomicInteger numberOfHundredDollarsBills = new AtomicInteger(0);
    private final AtomicInteger numberOfFiftyDollarsBills = new AtomicInteger(0);
    private final AtomicInteger numberOfTenDollarsBills = new AtomicInteger(0);
    
    // Session management for concurrent users
    private final Map<String, ATMSession> activeSessions = new ConcurrentHashMap<>();
    private final ReadWriteLock sessionLock = new ReentrantReadWriteLock();
    
    // Hardware component locks to prevent conflicts
    private final Lock cardReaderLock = new ReentrantLock();
    private final Lock cashDispenserLock = new ReentrantLock();
    private final Lock printerLock = new ReentrantLock();
    
    // Transaction processing
    private final ExecutorService transactionExecutor = Executors.newFixedThreadPool(10);
    private final Semaphore maxConcurrentUsers = new Semaphore(5); // Max 5 concurrent users
    
    // ATM status management
    private volatile ATMStatus atmStatus = ATMStatus.IDLE;
    private final Lock statusLock = new ReentrantLock();
    
    private ConcurrentATM() {
        this.cardReader = new CardReader();
        this.keyPad = new KeyPad();
        this.screen = new Screen();
        this.printer = new Printer();
        this.cashDispenser = new CashDispenser();
    }
    
    /**
     * Thread-safe singleton getInstance method
     */
    public static ConcurrentATM getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new ConcurrentATM();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize ATM with thread-safe cash management
     */
    public void initializeATM(int atmBalance, int noOfHundred, int noOfFifty, int noOfTen) {
        this.atmBalance.set(atmBalance);
        this.numberOfHundredDollarsBills.set(noOfHundred);
        this.numberOfFiftyDollarsBills.set(noOfFifty);
        this.numberOfTenDollarsBills.set(noOfTen);
        
        statusLock.lock();
        try {
            this.atmStatus = ATMStatus.IDLE;
        } finally {
            statusLock.unlock();
        }
    }
    
    /**
     * Create a new ATM session for a user
     */
    public String createSession(User user) throws InterruptedException {
        // Acquire semaphore to limit concurrent users
        maxConcurrentUsers.acquire();
        
        String sessionId = UUID.randomUUID().toString();
        ATMSession session = new ATMSession(sessionId, user);
        
        sessionLock.writeLock().lock();
        try {
            activeSessions.put(sessionId, session);
        } finally {
            sessionLock.writeLock().unlock();
        }
        
        return sessionId;
    }
    
    /**
     * End a session and release resources
     */
    public void endSession(String sessionId) {
        sessionLock.writeLock().lock();
        try {
            ATMSession session = activeSessions.remove(sessionId);
            if (session != null) {
                session.cleanup();
            }
        } finally {
            sessionLock.writeLock().unlock();
            maxConcurrentUsers.release();
        }
    }
    
    /**
     * Get session by ID (thread-safe)
     */
    public ATMSession getSession(String sessionId) {
        sessionLock.readLock().lock();
        try {
            return activeSessions.get(sessionId);
        } finally {
            sessionLock.readLock().unlock();
        }
    }
    
    /**
     * Process transaction in a separate thread
     */
    public CompletableFuture<TransactionResult> processTransaction(String sessionId, TransactionType type, Object... params) {
        return CompletableFuture.supplyAsync(() -> {
            ATMSession session = getSession(sessionId);
            if (session == null) {
                return new TransactionResult(false, "Session not found");
            }
            
            return session.processTransaction(type, params);
        }, transactionExecutor);
    }
    
    /**
     * Get ATM statistics
     */
    public ATMStatistics getATMStatistics() {
        sessionLock.readLock().lock();
        try {
            int totalActiveSessions = activeSessions.size();
            int totalCash = atmBalance.get();
            int totalHundredBills = numberOfHundredDollarsBills.get();
            int totalFiftyBills = numberOfFiftyDollarsBills.get();
            int totalTenBills = numberOfTenDollarsBills.get();
            
            return new ATMStatistics(totalActiveSessions, totalCash, totalHundredBills, totalFiftyBills, totalTenBills);
        } finally {
            sessionLock.readLock().unlock();
        }
    }
    
    /**
     * Thread-safe cash withdrawal with proper locking
     */
    public boolean withdrawCash(double amount) {
        cashDispenserLock.lock();
        try {
            if (amount > atmBalance.get()) {
                return false;
            }
            
            // Calculate bill distribution
            int[] bills = calculateBillDistribution(amount);
            if (bills == null) {
                return false; // Cannot provide exact change
            }
            
            // Atomic update of cash reserves
            atmBalance.addAndGet(-(int)amount);
            numberOfHundredDollarsBills.addAndGet(-bills[0]);
            numberOfFiftyDollarsBills.addAndGet(-bills[1]);
            numberOfTenDollarsBills.addAndGet(-bills[2]);
            
            return true;
        } finally {
            cashDispenserLock.unlock();
        }
    }
    
    /**
     * Calculate optimal bill distribution
     */
    private int[] calculateBillDistribution(double amount) {
        int remaining = (int) amount;
        int[] bills = new int[3]; // [hundreds, fifties, tens]
        
        int availableHundreds = numberOfHundredDollarsBills.get();
        int availableFifties = numberOfFiftyDollarsBills.get();
        int availableTens = numberOfTenDollarsBills.get();
        
        // Try to use hundreds first
        bills[0] = Math.min(remaining / 100, availableHundreds);
        remaining -= bills[0] * 100;
        
        // Then fifties
        bills[1] = Math.min(remaining / 50, availableFifties);
        remaining -= bills[1] * 50;
        
        // Finally tens
        bills[2] = Math.min(remaining / 10, availableTens);
        remaining -= bills[2] * 10;
        
        return remaining == 0 ? bills : null;
    }
    
    /**
     * Thread-safe card reading
     */
    public boolean readCard(AtmCard card) {
        cardReaderLock.lock();
        try {
            return cardReader.readCard(card);
        } finally {
            cardReaderLock.unlock();
        }
    }
    
    /**
     * Thread-safe printing
     */
    public void printReceipt(String receipt) {
        printerLock.lock();
        try {
            printer.printReceipt(receipt);
        } finally {
            printerLock.unlock();
        }
    }
    
    // Getters with thread safety
    public int getAtmBalance() {
        return atmBalance.get();
    }
    
    public int getNumberOfHundredDollarsBills() {
        return numberOfHundredDollarsBills.get();
    }
    
    public int getNumberOfFiftyDollarsBills() {
        return numberOfFiftyDollarsBills.get();
    }
    
    public int getNumberOfTenDollarsBills() {
        return numberOfTenDollarsBills.get();
    }
    
    public ATMStatus getAtmStatus() {
        statusLock.lock();
        try {
            return atmStatus;
        } finally {
            statusLock.unlock();
        }
    }
    
    public void setAtmStatus(ATMStatus status) {
        statusLock.lock();
        try {
            this.atmStatus = status;
        } finally {
            statusLock.unlock();
        }
    }
    
    public int getActiveSessionCount() {
        sessionLock.readLock().lock();
        try {
            return activeSessions.size();
        } finally {
            sessionLock.readLock().unlock();
        }
    }
    
    /**
     * Shutdown the ATM system gracefully
     */
    public void shutdown() {
        transactionExecutor.shutdown();
        try {
            if (!transactionExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                transactionExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            transactionExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public  void showMassage(String message){
        screen.showMessage(message);
    }
    
    /**
     * ATM Statistics class
     */
    public static class ATMStatistics {
        private final int activeSessions;
        private final int totalCash;
        private final int hundredBills;
        private final int fiftyBills;
        private final int tenBills;
        
        public ATMStatistics(int activeSessions, int totalCash, int hundredBills, int fiftyBills, int tenBills) {
            this.activeSessions = activeSessions;
            this.totalCash = totalCash;
            this.hundredBills = hundredBills;
            this.fiftyBills = fiftyBills;
            this.tenBills = tenBills;
        }
        
        public int getActiveSessions() { return activeSessions; }
        public int getTotalCash() { return totalCash; }
        public int getHundredBills() { return hundredBills; }
        public int getFiftyBills() { return fiftyBills; }
        public int getTenBills() { return tenBills; }
        
        @Override
        public String toString() {
            return String.format("ATMStatistics{ActiveSessions=%d, TotalCash=$%d, HundredBills=%d, FiftyBills=%d, TenBills=%d}", 
                               activeSessions, totalCash, hundredBills, fiftyBills, tenBills);
        }



    }
}
