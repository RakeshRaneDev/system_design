package org.byte_stroke.inmemory_database;

import java.time.Instant;
import java.util.*;

/**
 * Simple test runner to demonstrate the in-memory database functionality
 */
public class SimpleTestRunner {
    public static void main(String[] args) {
        System.out.println("=== InMemory Database Simple Test ===\n");
        
        // Create database instance
        InMemoryDatabase db = new InMemoryDatabase("test-db", "./test-backups");
        db.start();
        
        try {
            // Test 1: Basic CRUD Operations
            System.out.println("1. Testing Basic CRUD Operations:");
            System.out.println("   Creating entries...");
            db.create("user:1", "John Doe");
            db.create("user:2", "Jane Smith");
            db.create("user:3", "Bob Johnson");
            
            System.out.println("   Reading entries:");
            System.out.println("     user:1 = " + db.read("user:1"));
            System.out.println("     user:2 = " + db.read("user:2"));
            System.out.println("     user:3 = " + db.read("user:3"));
            
            System.out.println("   Database size: " + db.size());
            System.out.println("   All keys: " + db.keys());
            
            // Test 2: TTL Operations
            System.out.println("\n2. Testing TTL Operations:");
            System.out.println("   Creating entries with TTL...");
            db.create("session:1", "active_session_1", 3L); // 3 seconds TTL
            db.create("session:2", "active_session_2", 5L); // 5 seconds TTL
            
            System.out.println("   session:1 TTL remaining: " + db.getRemainingTTL("session:1") + "ms");
            System.out.println("   session:2 TTL remaining: " + db.getRemainingTTL("session:2") + "ms");
            
            System.out.println("   Waiting 4 seconds for expiration...");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            System.out.println("   After 4 seconds:");
            System.out.println("     session:1 exists: " + db.exists("session:1"));
            System.out.println("     session:2 exists: " + db.exists("session:2"));
            
            // Test 3: Advanced Filtering
            System.out.println("\n3. Testing Advanced Filtering:");
            System.out.println("   Creating entries with metadata...");
            
            Map<String, Object> user1Meta = new HashMap<>();
            user1Meta.put("age", 25);
            user1Meta.put("city", "New York");
            user1Meta.put("role", "admin");
            
            Map<String, Object> user2Meta = new HashMap<>();
            user2Meta.put("age", 30);
            user2Meta.put("city", "Los Angeles");
            user2Meta.put("role", "user");
            
            db.create("user:alice", "Alice Johnson", null, user1Meta);
            db.create("user:bob", "Bob Smith", null, user2Meta);
            
            System.out.println("   Searching for 'Alice':");
            List<DatabaseEntry> results = db.search("Alice");
            results.forEach(entry -> System.out.println("     " + entry.getKey() + " = " + entry.getValue()));
            
            System.out.println("   Filtering by city = 'New York':");
            QueryCondition cityCondition = new QueryCondition("city", "New York", QueryCondition.ComparisonOperator.EQUALS);
            results = db.find(cityCondition);
            results.forEach(entry -> System.out.println("     " + entry.getKey() + " = " + entry.getValue()));
            
            // Test 4: Backup and Restore
            System.out.println("\n4. Testing Backup and Restore:");
            System.out.println("   Creating backup...");
            String backupPath = db.createBackup();
            System.out.println("   Backup created at: " + backupPath);
            
            System.out.println("   Database size before clear: " + db.size());
            db.clear();
            System.out.println("   Database size after clear: " + db.size());
            
            System.out.println("   Restoring from backup...");
            boolean restored = db.restoreFromBackup(backupPath);
            System.out.println("   Restore successful: " + restored);
            System.out.println("   Database size after restore: " + db.size());
            
            // Test 5: Statistics
            System.out.println("\n5. Database Statistics:");
            InMemoryDatabase.DatabaseStats stats = db.getStats();
            System.out.println("   " + stats);
            
            TTLManager.TTLStats ttlStats = db.ttlManager.getTTLStats();
            System.out.println("   TTL Stats: " + ttlStats);
            
            System.out.println("\n=== All Tests Completed Successfully! ===");
            
        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.stop();
        }
    }
}
