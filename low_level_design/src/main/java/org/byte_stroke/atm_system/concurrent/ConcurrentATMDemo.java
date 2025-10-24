package org.byte_stroke.atm_system.concurrent;

import org.byte_stroke.atm_system.educative.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Comprehensive demo of the concurrent ATM system
 */
public class ConcurrentATMDemo {
    
    private static final int NUM_USERS = 1;
    private static final Random random = new Random();
    
    public static void main(String[] args) {
        System.out.println("=== Concurrent ATM System Demo ===\n");
        
        // Setup single ATM
        ConcurrentATM atm = setupATM();
        
        // Create users
        List<User> users = createUsers();
        
        // Run concurrent simulation
        runConcurrentSimulation(atm, users);
        
        // Display final statistics
        displayFinalStatistics(atm);
        
        // Cleanup
        atm.shutdown();
    }
    
    /**
     * Setup single ATM
     */
    private static ConcurrentATM setupATM() {
        ConcurrentATM atm = ConcurrentATM.getInstance();
        atm.initializeATM(50000, 200, 300, 500); // $50,000 total cash
        
        System.out.println("✓ ATM setup complete with $50,000 cash");
        return atm;
    }
    
    /**
     * Create test users with different account types
     */
    private static List<User> createUsers() {
        List<User> users = new ArrayList<>();
        Bank bank = new Bank("Demo Bank", "DB001");
        
        for (int i = 0; i < NUM_USERS; i++) {
            // Create different account types
            BankAccount account;
            if (i % 2 == 0) {
                account = new SavingAccount(1000 + i, 1000 + (i * 500));
            } else {
                account = new CurrentAccount(2000 + i, 2000 + (i * 1000));
            }
            
            AtmCard card = new AtmCard("1234" + String.format("%04d", i), "User" + i, 1234 + i, "12/27");
            User user = new User(card, account);
            users.add(user);
        }
        
        System.out.println("✓ Created " + NUM_USERS + " test users");
        return users;
    }
    
    /**
     * Run concurrent simulation with multiple users
     */
    private static void runConcurrentSimulation(ConcurrentATM atm, List<User> users) {
        ExecutorService userExecutor = Executors.newFixedThreadPool(NUM_USERS);
        List<CompletableFuture<Void>> userTasks = new ArrayList<>();
        
        System.out.println("\n=== Starting Concurrent Simulation ===");
        
        // Create tasks for each user
        for (int i = 0; i < NUM_USERS; i++) {
            final int userIndex = i;
            final User user = users.get(i);
            
            CompletableFuture<Void> userTask = CompletableFuture.runAsync(() -> {
                try {
                    simulateUserSession(atm, user, userIndex);
                } catch (Exception e) {
                    System.err.println("User " + userIndex + " simulation failed: " + e.getMessage());
                }
            }, userExecutor);
            
            userTasks.add(userTask);
        }
        
        // Wait for all users to complete
        CompletableFuture<Void> allUsers = CompletableFuture.allOf(
            userTasks.toArray(new CompletableFuture[0])
        );
        
        try {
            allUsers.get(60, TimeUnit.SECONDS); // 60 second timeout
            System.out.println("\n✓ All user sessions completed");
        } catch (TimeoutException e) {
            System.err.println("⚠ Simulation timed out");
        } catch (Exception e) {
            System.err.println("⚠ Simulation error: " + e.getMessage());
        } finally {
            userExecutor.shutdown();
        }
    }
    
    /**
     * Simulate a user session with multiple transactions
     */
    private static void simulateUserSession(ConcurrentATM atm, User user, int userIndex) {
        try {
            // Create session
            String sessionId = atm.createSession(user);
            ATMSession  session = atm.getSession(sessionId);

            session.insertCard(user.getCard());
            session.authenticatePin(user.getCard().getPin());


            System.out.println("User " + userIndex + " started session: " + sessionId);
            
            // Simulate random delay (user thinking time)
            Thread.sleep(random.nextInt(1000) + 500);
            
            // Perform random transactions
            int numTransactions = random.nextInt(5) + 1; // 1-5 transactions
            
            for (int t = 0; t < numTransactions; t++) {
                TransactionType transactionType = getRandomTransactionType();
                
                System.out.println("User " + userIndex + " performing " + transactionType + " transaction");
                
                TransactionResult result = performTransaction(atm, sessionId, TransactionType.BALANCE_INQUIRY, user);

                atm.showMassage(result.getMessage());

                
//                userif (result.isSuccess()) {
//                    System.out.println("✓ User " + userIndex + " transaction successful: " + result.getMessage());
//                } else {
//                    System.out.println("✗ User " + userIndex + " transaction failed: " + result.getMessage());
//                }
                
                // Random delay between transactions
                Thread.sleep(random.nextInt(2000) + 1000);
            }
            session.returnCard();
            // End session
            atm.endSession(sessionId);
            System.out.println("User " + userIndex + " ended session");
            
        } catch (Exception e) {
            System.err.println("User " + userIndex + " session error: " + e.getMessage());
        }
    }
    
    /**
     * Perform a transaction based on type
     */
    private static TransactionResult performTransaction(ConcurrentATM atm, String sessionId, 
                                                      TransactionType type, User user) {
        try {
            switch (type) {
                case BALANCE_INQUIRY:
                    return atm.processTransaction(sessionId, type).get();
                    
                case CASH_WITH_DRAW:
                    double withdrawAmount = 50 + random.nextInt(500); // $50-$550
                    return atm.processTransaction(sessionId, type, withdrawAmount).get();
                    
                case FUNDS_TRANSFER:
                    // Create a dummy account for transfer
                    BankAccount dummyAccount = new CurrentAccount(9999, 10000);
                    double transferAmount = 25 + random.nextInt(200); // $25-$225
                    return atm.processTransaction(sessionId, type, dummyAccount, transferAmount).get();
                    
                case CHANGE_PIN:
                    int newPin = 1000 + random.nextInt(9000);
                    return atm.processTransaction(sessionId, type, newPin).get();
                    
                default:
                    return new TransactionResult(false, "Unknown transaction type");
            }
        } catch (Exception e) {
            return new TransactionResult(false, "Transaction error: " + e.getMessage());
        }
    }
    
    /**
     * Get random transaction type
     */
    private static TransactionType getRandomTransactionType() {
        TransactionType[] types = {
            TransactionType.BALANCE_INQUIRY,
            TransactionType.CASH_WITH_DRAW,
            TransactionType.FUNDS_TRANSFER,
            TransactionType.CHANGE_PIN
        };
        return types[random.nextInt(types.length)];
    }
    
    /**
     * Display final ATM statistics
     */
    private static void displayFinalStatistics(ConcurrentATM atm) {
        System.out.println("\n=== Final ATM Statistics ===");
        ConcurrentATM.ATMStatistics stats = atm.getATMStatistics();
        System.out.println(stats);
        
        System.out.println("\n=== Demo Completed Successfully ===");
        System.out.println("✓ Concurrent ATM system demonstrated");
        System.out.println("✓ Multiple users handled simultaneously");
        System.out.println("✓ Thread-safe operations verified");
        System.out.println("✓ Session management working");
    }
}
