package org.byte_stroke.inmemory_database;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Demo application showcasing all features of the InMemoryDatabase
 */
public class InMemoryDatabaseDemo {
    public static void main(String[] args) {
        System.out.println("=== InMemory Database Demo ===\n");
        
        // Create database instance
        InMemoryDatabase db = new InMemoryDatabase("demo-db", "./demo-backups");
        db.start();
        
        try {
            // Demo 1: Basic CRUD Operations
            demonstrateBasicCRUD(db);
            
            // Demo 2: TTL Operations
            demonstrateTTLOperations(db);
            
            // Demo 3: Advanced Filtering and Search
            demonstrateAdvancedFiltering(db);
            
            // Demo 4: Backup and Restore
            demonstrateBackupRestore(db);
            
            // Demo 5: Performance and Statistics
            demonstratePerformanceAndStats(db);
            
        } finally {
            db.stop();
        }
    }
    
    private static void demonstrateBasicCRUD(InMemoryDatabase db) {
        System.out.println("=== 1. Basic CRUD Operations ===");
        
        // Create operations
        System.out.println("Creating entries...");
        db.create("user:1", "John Doe");
        db.create("user:2", "Jane Smith");
        db.create("user:3", "Bob Johnson");
        
        // Read operations
        System.out.println("Reading entries...");
        System.out.println("user:1 = " + db.read("user:1"));
        System.out.println("user:2 = " + db.read("user:2"));
        System.out.println("user:3 = " + db.read("user:3"));
        
        // Update operations
        System.out.println("\nUpdating entries...");
        db.update("user:1", "John Doe (Updated)");
        System.out.println("user:1 after update = " + db.read("user:1"));
        
        // Upsert operations
        System.out.println("\nUpsert operations...");
        db.upsert("user:4", "Alice Brown"); // Create new
        db.upsert("user:1", "John Doe (Upserted)"); // Update existing
        System.out.println("user:4 = " + db.read("user:4"));
        System.out.println("user:1 after upsert = " + db.read("user:1"));
        
        // Check existence and size
        System.out.println("\nDatabase size: " + db.size());
        System.out.println("user:1 exists: " + db.exists("user:1"));
        System.out.println("user:5 exists: " + db.exists("user:5"));
        
        System.out.println("\nAll keys: " + db.keys());
        System.out.println("All values: " + db.values());
        
        System.out.println();
    }
    
    private static void demonstrateTTLOperations(InMemoryDatabase db) {
        System.out.println("=== 2. TTL (Time To Live) Operations ===");
        
        // Create entries with TTL
        System.out.println("Creating entries with TTL...");
        db.create("session:1", "active_session_1", 5L); // 5 seconds TTL
        db.create("session:2", "active_session_2", 10L); // 10 seconds TTL
        db.create("permanent:1", "permanent_data"); // No TTL
        
        System.out.println("session:1 TTL remaining: " + db.getRemainingTTL("session:1") + "ms");
        System.out.println("session:2 TTL remaining: " + db.getRemainingTTL("session:2") + "ms");
        System.out.println("permanent:1 TTL remaining: " + db.getRemainingTTL("permanent:1") + "ms");
        
        // Extend TTL
        System.out.println("\nExtending TTL for session:1...");
        db.extendTTL("session:1", 10L);
        System.out.println("session:1 TTL remaining after extension: " + db.getRemainingTTL("session:1") + "ms");
        
        // Set new TTL
        System.out.println("\nSetting new TTL for permanent:1...");
        db.setTTL("permanent:1", 3L);
        System.out.println("permanent:1 TTL remaining: " + db.getRemainingTTL("permanent:1") + "ms");
        
        // Wait for some entries to expire
        System.out.println("\nWaiting for some entries to expire...");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("After 4 seconds:");
        System.out.println("session:1 exists: " + db.exists("session:1"));
        System.out.println("session:2 exists: " + db.exists("session:2"));
        System.out.println("permanent:1 exists: " + db.exists("permanent:1"));
        
        // Manual cleanup
        int cleaned = db.ttlManager.cleanupExpiredEntries();
        System.out.println("Manually cleaned " + cleaned + " expired entries");
        
        System.out.println();
    }
    
    private static void demonstrateAdvancedFiltering(InMemoryDatabase db) {
        System.out.println("=== 3. Advanced Filtering and Search ===");
        
        // Create test data with metadata
        Map<String, Object> user1Meta = new HashMap<>();
        user1Meta.put("age", 25);
        user1Meta.put("city", "New York");
        user1Meta.put("role", "admin");
        
        Map<String, Object> user2Meta = new HashMap<>();
        user2Meta.put("age", 30);
        user2Meta.put("city", "Los Angeles");
        user2Meta.put("role", "user");
        
        Map<String, Object> user3Meta = new HashMap<>();
        user3Meta.put("age", 35);
        user3Meta.put("city", "New York");
        user3Meta.put("role", "user");
        
        db.create("user:alice", "Alice Johnson", null, user1Meta);
        db.create("user:bob", "Bob Smith", null, user2Meta);
        db.create("user:charlie", "Charlie Brown", null, user3Meta);
        
        // Search by value content
        System.out.println("Searching for 'Alice':");
        List<DatabaseEntry> results = db.search("Alice");
        results.forEach(entry -> System.out.println("  " + entry.getKey() + " = " + entry.getValue()));
        
        // Search by key pattern
        System.out.println("\nSearching by key pattern 'user':");
        results = db.searchByKey("user");
        results.forEach(entry -> System.out.println("  " + entry.getKey() + " = " + entry.getValue()));
        
        // Filter by metadata
        System.out.println("\nFiltering by city = 'New York':");
        QueryCondition cityCondition = new QueryCondition("city", "New York", QueryCondition.ComparisonOperator.EQUALS);
        results = db.find(cityCondition);
        results.forEach(entry -> System.out.println("  " + entry.getKey() + " = " + entry.getValue()));
        
        // Filter by age range
        System.out.println("\nFiltering by age >= 30:");
        QueryCondition ageCondition = new QueryCondition("age", 30, QueryCondition.ComparisonOperator.GREATER_THAN_OR_EQUALS);
        results = db.find(ageCondition);
        results.forEach(entry -> System.out.println("  " + entry.getKey() + " = " + entry.getValue()));
        
        // Multiple conditions
        System.out.println("\nFiltering by city = 'New York' AND role = 'user':");
        List<QueryCondition> conditions = Arrays.asList(
            new QueryCondition("city", "New York", QueryCondition.ComparisonOperator.EQUALS),
            new QueryCondition("role", "user", QueryCondition.ComparisonOperator.EQUALS)
        );
        results = db.find(conditions);
        results.forEach(entry -> System.out.println("  " + entry.getKey() + " = " + entry.getValue()));
        
        // Pagination
        System.out.println("\nPagination (limit 2, offset 1):");
        results = db.find(
            Collections.singletonList(new QueryCondition("key", "user", QueryCondition.ComparisonOperator.CONTAINS)),
            2, 1
        );
        results.forEach(entry -> System.out.println("  " + entry.getKey() + " = " + entry.getValue()));
        
        // Time-based filtering
        System.out.println("\nTime-based filtering (entries created in last minute):");
        Instant oneMinuteAgo = Instant.now().minusSeconds(60);
        Instant now = Instant.now();
        results = db.findByCreatedAtRange(oneMinuteAgo, now);
        results.forEach(entry -> System.out.println("  " + entry.getKey() + " = " + entry.getValue()));
        
        System.out.println();
    }
    
    private static void demonstrateBackupRestore(InMemoryDatabase db) {
        System.out.println("=== 4. Backup and Restore Operations ===");
        
        // Create some data
        System.out.println("Creating data for backup...");
        db.create("backup:1", "Important Data 1");
        db.create("backup:2", "Important Data 2", 30L);
        Map<String, Object> meta = new HashMap<>();
        meta.put("version", "1.0");
        db.create("backup:3", "Important Data 3", null, meta);
        
        System.out.println("Database size before backup: " + db.size());
        
        // Create backup
        System.out.println("Creating backup...");
        String backupPath = db.createBackup();
        System.out.println("Backup created at: " + backupPath);
        
        // List available backups
        System.out.println("\nAvailable backups:");
        String[] backups = db.listBackups();
        for (String backup : backups) {
            System.out.println("  " + backup);
        }
        
        // Clear database
        System.out.println("\nClearing database...");
        db.clear();
        System.out.println("Database size after clear: " + db.size());
        
        // Restore from backup
        System.out.println("Restoring from backup...");
        boolean restored = db.restoreFromBackup(backupPath);
        System.out.println("Restore successful: " + restored);
        System.out.println("Database size after restore: " + db.size());
        
        // Verify restored data
        System.out.println("Verifying restored data:");
        System.out.println("  backup:1 = " + db.read("backup:1"));
        System.out.println("  backup:2 = " + db.read("backup:2"));
        System.out.println("  backup:3 = " + db.read("backup:3"));
        
        // Check metadata
        DatabaseEntry entry = db.readEntry("backup:3");
        if (entry != null && entry.getMetadata() != null) {
            System.out.println("  backup:3 metadata = " + entry.getMetadata());
        }
        
        // Cleanup old backups
        System.out.println("\nCleaning up old backups (keeping only 2)...");
        db.cleanupOldBackups(2);
        System.out.println("Backups after cleanup:");
        backups = db.listBackups();
        for (String backup : backups) {
            System.out.println("  " + backup);
        }
        
        System.out.println();
    }
    
    private static void demonstratePerformanceAndStats(InMemoryDatabase db) {
        System.out.println("=== 5. Performance and Statistics ===");
        
        // Performance test
        System.out.println("Performance test - creating 1000 entries...");
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 1000; i++) {
            db.create("perf:key" + i, "value" + i);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Time to create 1000 entries: " + (endTime - startTime) + "ms");
        
        // Search performance
        System.out.println("\nSearch performance test...");
        startTime = System.currentTimeMillis();
        
        List<DatabaseEntry> results = db.search("value5");
        
        endTime = System.currentTimeMillis();
        System.out.println("Time to search: " + (endTime - startTime) + "ms");
        System.out.println("Search results count: " + results.size());
        
        // Database statistics
        System.out.println("\nDatabase Statistics:");
        InMemoryDatabase.DatabaseStats stats = db.getStats();
        System.out.println("  " + stats);
        
        // TTL statistics
        TTLManager.TTLStats ttlStats = db.ttlManager.getTTLStats();
        System.out.println("  TTL Stats: " + ttlStats);
        
        // Memory usage estimation
        System.out.println("\nMemory Usage Estimation:");
        System.out.println("  Total entries: " + db.size());
        System.out.println("  Keys: " + db.keys().size());
        System.out.println("  Values: " + db.values().size());
        
        // Cleanup
        System.out.println("\nCleaning up test data...");
        db.clear();
        System.out.println("Database size after cleanup: " + db.size());
        
        System.out.println();
    }
}
