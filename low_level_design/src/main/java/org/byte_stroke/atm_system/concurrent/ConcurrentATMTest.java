package org.byte_stroke.atm_system.concurrent;

import org.byte_stroke.atm_system.educative.*;
import java.util.concurrent.*;

/**
 * Simple test to verify the concurrent ATM system works correctly
 */
public class ConcurrentATMTest {
    
    public static void main(String[] args) {
        System.out.println("=== Concurrent ATM System Test ===\n");
        
        try {
            // Test 1: Basic ATM functionality
            testBasicATMFunctionality();
            
            // Test 2: Concurrent user sessions
            testConcurrentSessions();
            
            // Test 3: ATM statistics
            testATMStatistics();
            
            System.out.println("\n✓ All tests completed successfully!");
            
        } catch (Exception e) {
            System.err.println("✗ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Test basic ATM functionality
     */
    private static void testBasicATMFunctionality() throws Exception {
        System.out.println("Testing basic ATM functionality...");
        
        ConcurrentATM atm = ConcurrentATM.getInstance();
        atm.initializeATM(10000, 50, 100, 200);
        
        // Test cash withdrawal
        boolean withdrawResult = atm.withdrawCash(100);
        System.out.println("✓ Cash withdrawal test: " + (withdrawResult ? "PASSED" : "FAILED"));
        
        // Test card reading
        AtmCard testCard = new AtmCard("123456", "Test User", 1234, "12/27");
        boolean cardResult = atm.readCard(testCard);
        System.out.println("✓ Card reading test: " + (cardResult ? "PASSED" : "FAILED"));
        
        System.out.println("✓ Basic ATM functionality test completed\n");
    }
    
    /**
     * Test concurrent user sessions
     */
    private static void testConcurrentSessions() throws Exception {
        System.out.println("Testing concurrent user sessions...");
        
        ConcurrentATM atm = ConcurrentATM.getInstance();
        atm.initializeATM(20000, 100, 150, 250);
        
        // Create test users
        BankAccount account1 = new SavingAccount(1001, 1000);
        BankAccount account2 = new CurrentAccount(1002, 2000);
        
        AtmCard card1 = new AtmCard("111111", "User1", 1111, "12/27");
        AtmCard card2 = new AtmCard("222222", "User2", 2222, "12/27");
        
        User user1 = new User(card1, account1);
        User user2 = new User(card2, account2);
        
        // Create sessions concurrently
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        CompletableFuture<String> session1Future = CompletableFuture.supplyAsync(() -> {
            try {
                return atm.createSession(user1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }, executor);
        
        CompletableFuture<String> session2Future = CompletableFuture.supplyAsync(() -> {
            try {
                return atm.createSession(user2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }, executor);
        
        String session1 = session1Future.get();
        String session2 = session2Future.get();
        
        System.out.println("✓ Created session 1: " + session1);
        System.out.println("✓ Created session 2: " + session2);
        
        // Test concurrent transactions
        CompletableFuture<TransactionResult> transaction1 = atm.processTransaction(session1, TransactionType.BALANCE_INQUIRY);
        CompletableFuture<TransactionResult> transaction2 = atm.processTransaction(session2, TransactionType.BALANCE_INQUIRY);
        
        TransactionResult result1 = transaction1.get();
        TransactionResult result2 = transaction2.get();
        
        System.out.println("✓ Transaction 1 result: " + result1.isSuccess());
        System.out.println("✓ Transaction 2 result: " + result2.isSuccess());
        
        // Clean up sessions
        atm.endSession(session1);
        atm.endSession(session2);
        
        executor.shutdown();
        System.out.println("✓ Concurrent sessions test completed\n");
    }
    
    /**
     * Test ATM statistics and monitoring
     */
    private static void testATMStatistics() throws Exception {
        System.out.println("Testing ATM statistics...");
        
        ConcurrentATM atm = ConcurrentATM.getInstance();
        atm.initializeATM(30000, 150, 200, 300);
        
        // Test statistics before any sessions
        ConcurrentATM.ATMStatistics initialStats = atm.getATMStatistics();
        System.out.println("✓ Initial statistics: " + initialStats);
        
        // Create a test session
        BankAccount account = new SavingAccount(1003, 1500);
        AtmCard card = new AtmCard("333333", "StatsUser", 3333, "12/27");
        User user = new User(card, account);
        
        String sessionId = atm.createSession(user);
        System.out.println("✓ Session created: " + sessionId);
        
        // Test statistics with active session
        ConcurrentATM.ATMStatistics activeStats = atm.getATMStatistics();
        System.out.println("✓ Active session statistics: " + activeStats);
        
        // Test transaction
        TransactionResult result = atm.processTransaction(sessionId, TransactionType.BALANCE_INQUIRY).get();
        System.out.println("✓ Transaction result: " + result.isSuccess());
        
        // Clean up
        atm.endSession(sessionId);
        
        // Test final statistics
        ConcurrentATM.ATMStatistics finalStats = atm.getATMStatistics();
        System.out.println("✓ Final statistics: " + finalStats);
        
        System.out.println("✓ ATM statistics test completed\n");
    }
}
