package org.byte_stroke.inmemory_database;


import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for InMemoryDatabase
 */
public class InMemoryDatabaseTest {
    private InMemoryDatabase database;

    @BeforeEach
    void setUp() {
        database = new InMemoryDatabase("test-db", "./test-backups");
        database.start();
    }

    @AfterEach
    void tearDown() {
        database.stop();
    }

    // ========== Basic CRUD Tests ==========

    @Test
    void testCreateAndRead() {
        // Test basic create and read
        assertTrue(database.create("key1", "value1"));
        assertEquals("value1", database.read("key1"));
        
        // Test duplicate key creation
        assertFalse(database.create("key1", "value2"));
        assertEquals("value1", database.read("key1"));
        
        // Test null key/value
        assertFalse(database.create(null, "value"));
        assertFalse(database.create("key", null));
    }

    @Test
    void testUpsert() {
        // Test upsert (create)
        assertTrue(database.upsert("key1", "value1"));
        assertEquals("value1", database.read("key1"));
        
        // Test upsert (update)
        assertTrue(database.upsert("key1", "value2"));
        assertEquals("value2", database.read("key1"));
    }

    @Test
    void testUpdate() {
        // Test update existing key
        database.create("key1", "value1");
        assertTrue(database.update("key1", "value2"));
        assertEquals("value2", database.read("key1"));
        
        // Test update non-existing key
        assertFalse(database.update("key2", "value2"));
        assertNull(database.read("key2"));
    }

    @Test
    void testDelete() {
        // Test delete existing key
        database.create("key1", "value1");
        assertTrue(database.delete("key1"));
        assertNull(database.read("key1"));
        
        // Test delete non-existing key
        assertFalse(database.delete("key2"));
    }

    @Test
    void testDeleteAll() {
        database.create("key1", "value1");
        database.create("key2", "value2");
        database.create("key3", "value3");
        
        List<String> keysToDelete = Arrays.asList("key1", "key2", "key4");
        int deletedCount = database.deleteAll(keysToDelete);
        
        assertEquals(2, deletedCount);
        assertNull(database.read("key1"));
        assertNull(database.read("key2"));
        assertEquals("value3", database.read("key3"));
    }

    @Test
    void testExists() {
        assertFalse(database.exists("key1"));
        database.create("key1", "value1");
        assertTrue(database.exists("key1"));
    }

    @Test
    void testSizeAndEmpty() {
        assertTrue(database.isEmpty());
        assertEquals(0, database.size());
        
        database.create("key1", "value1");
        assertFalse(database.isEmpty());
        assertEquals(1, database.size());
    }

    @Test
    void testKeysAndValues() {
        database.create("key1", "value1");
        database.create("key2", "value2");
        
        Set<String> keys = database.keys();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("key1"));
        assertTrue(keys.contains("key2"));
        
        List<Object> values = database.values();
        assertEquals(2, values.size());
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }

    @Test
    void testClear() {
        database.create("key1", "value1");
        database.create("key2", "value2");
        assertEquals(2, database.size());
        
        database.clear();
        assertTrue(database.isEmpty());
        assertEquals(0, database.size());
    }

    // ========== TTL Tests ==========

    @Test
    void testTTLBasic() {
        // Test create with TTL
        assertTrue(database.create("key1", "value1", 1L)); // 1 second TTL
        assertEquals("value1", database.read("key1"));
        
        // Wait for expiration
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertNull(database.read("key1"));
    }

    @Test
    void testTTLOperations() {
        database.create("key1", "value1", 10L);
        
        // Test get remaining TTL
        long remaining = database.getRemainingTTL("key1");
        assertTrue(remaining > 0);
        assertTrue(remaining <= 10000); // Should be <= 10 seconds in milliseconds
        
        // Test extend TTL
        assertTrue(database.extendTTL("key1", 5));
        long newRemaining = database.getRemainingTTL("key1");
        assertTrue(newRemaining > remaining);
        
        // Test set TTL
        assertTrue(database.setTTL("key1", 20L));
        long setRemaining = database.getRemainingTTL("key1");
        assertTrue(setRemaining > 0);
        
        // Test remove TTL
        assertTrue(database.removeTTL("key1"));
        assertEquals(-1, database.getRemainingTTL("key1"));
    }

    @Test
    void testTTLWithMetadata() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("type", "test");
        metadata.put("priority", 1);
        
        database.create("key1", "value1", 5L, metadata);
        DatabaseEntry entry = database.readEntry("key1");
        
        assertNotNull(entry);
        assertEquals("test", entry.getMetadata().get("type"));
        assertEquals(1, entry.getMetadata().get("priority"));
    }

    // ========== Advanced Filtering Tests ==========

    @Test
    void testFindByCondition() {
        database.create("user1", "John");
        database.create("user2", "Jane");
        database.create("user3", "Bob");
        
        // Test find by value
        List<DatabaseEntry> results = database.find(
            new QueryCondition("value", "John", QueryCondition.ComparisonOperator.EQUALS)
        );
        assertEquals(1, results.size());
        assertEquals("user1", results.get(0).getKey());
        
        // Test find by key pattern
        results = database.find(
            new QueryCondition("key", "user", QueryCondition.ComparisonOperator.CONTAINS)
        );
        assertEquals(3, results.size());
    }

    @Test
    void testFindWithMultipleConditions() {
        database.create("user1", "John");
        database.create("user2", "Jane");
        database.create("admin1", "Admin");
        
        List<QueryCondition> conditions = Arrays.asList(
            new QueryCondition("key", "user", QueryCondition.ComparisonOperator.CONTAINS),
            new QueryCondition("value", "J", QueryCondition.ComparisonOperator.STARTS_WITH)
        );
        
        List<DatabaseEntry> results = database.find(conditions);
        assertEquals(2, results.size());
    }

    @Test
    void testSearch() {
        database.create("key1", "Hello World");
        database.create("key2", "Hello Java");
        database.create("key3", "Goodbye World");
        
        List<DatabaseEntry> results = database.search("Hello");
        assertEquals(2, results.size());
        
        results = database.search("World");
        assertEquals(2, results.size());
    }

    @Test
    void testSearchByKey() {
        database.create("user_001", "John");
        database.create("admin_001", "Admin");
        database.create("guest_001", "Guest");
        
        List<DatabaseEntry> results = database.searchByKey("user");
        assertEquals(1, results.size());
        assertEquals("user_001", results.get(0).getKey());
    }

    @Test
    void testFindByTimeRange() {
        Instant now = Instant.now();
        database.create("key1", "value1");
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        database.create("key2", "value2");
        
        Instant start = now.minusSeconds(1);
        Instant end = now.plusSeconds(1);
        
        List<DatabaseEntry> results = database.findByCreatedAtRange(start, end);
        assertEquals(2, results.size());
    }

    @Test
    void testFindWithPagination() {
        // Create test data
        for (int i = 1; i <= 10; i++) {
            database.create("key" + i, "value" + i);
        }
        
        // Test with limit
        List<DatabaseEntry> results = database.find(
            Collections.singletonList(new QueryCondition("key", "key", QueryCondition.ComparisonOperator.CONTAINS)),
            5, null
        );
        assertEquals(5, results.size());
        
        // Test with offset
        results = database.find(
            Collections.singletonList(new QueryCondition("key", "key", QueryCondition.ComparisonOperator.CONTAINS)),
            null, 5
        );
        assertEquals(5, results.size());
        
        // Test with both limit and offset
        results = database.find(
            Collections.singletonList(new QueryCondition("key", "key", QueryCondition.ComparisonOperator.CONTAINS)),
            3, 2
        );
        assertEquals(3, results.size());
    }

    // ========== Backup Tests ==========

    @Test
    void testBackupAndRestore() {
        // Create test data
        database.create("key1", "value1");
        database.create("key2", "value2", 10L);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("type", "test");
        database.create("key3", "value3", null, metadata);
        
        // Create backup
        String backupPath = database.createBackup();
        assertNotNull(backupPath);
        
        // Clear database
        database.clear();
        assertTrue(database.isEmpty());
        
        // Restore from backup
        assertTrue(database.restoreFromBackup(backupPath));
        assertEquals(3, database.size());
        assertEquals("value1", database.read("key1"));
        assertEquals("value2", database.read("key2"));
        assertEquals("value3", database.read("key3"));
        
        // Verify metadata
        DatabaseEntry entry = database.readEntry("key3");
        assertEquals("test", entry.getMetadata().get("type"));
    }

    @Test
    void testListBackups() {
        database.create("key1", "value1");
        String backup1 = database.createBackup();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String backup2 = database.createBackup();
        
        String[] backups = database.listBackups();
        assertTrue(backups.length >= 2);
    }

    @Test
    void testCleanupOldBackups() {
        // Create multiple backups
        for (int i = 0; i < 5; i++) {
            database.create("key" + i, "value" + i);
            database.createBackup();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Cleanup old backups, keep only 2
        database.cleanupOldBackups(2);
        
        String[] backups = database.listBackups();
        assertTrue(backups.length <= 2);
    }

    // ========== Concurrency Tests ==========

    @Test
    void testConcurrentOperations() throws InterruptedException {
        int numThreads = 10;
        int operationsPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < operationsPerThread; j++) {
                        String key = "thread" + threadId + "_key" + j;
                        String value = "value" + j;
                        
                        database.create(key, value);
                        assertEquals(value, database.read(key));
                        database.update(key, value + "_updated");
                        assertEquals(value + "_updated", database.read(key));
                        database.delete(key);
                        assertNull(database.read(key));
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(30, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Database should be empty after all operations
        assertTrue(database.isEmpty());
    }

    @Test
    void testConcurrentReadWrite() throws InterruptedException {
        int numReaders = 5;
        int numWriters = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numReaders + numWriters);
        CountDownLatch latch = new CountDownLatch(numReaders + numWriters);
        
        // Create initial data
        for (int i = 0; i < 10; i++) {
            database.create("key" + i, "value" + i);
        }
        
        // Start readers
        for (int i = 0; i < numReaders; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
                        for (int k = 0; k < 10; k++) {
                            database.read("key" + k);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Start writers
        for (int i = 0; i < numWriters; i++) {
            final int writerId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 50; j++) {
                        String key = "writer" + writerId + "_key" + j;
                        database.create(key, "value" + j);
                        database.update(key, "updated" + j);
                        database.delete(key);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(30, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Original data should still be there
        assertEquals(10, database.size());
        for (int i = 0; i < 10; i++) {
            assertEquals("value" + i, database.read("key" + i));
        }
    }

    // ========== Statistics Tests ==========

    @Test
    void testDatabaseStats() {
        InMemoryDatabase.DatabaseStats stats = database.getStats();
        assertEquals("test-db", stats.getName());
        assertTrue(stats.isStarted());
        assertEquals(0, stats.getTotalEntries());
        
        database.create("key1", "value1");
        database.create("key2", "value2", 10L);
        
        stats = database.getStats();
        assertEquals(2, stats.getTotalEntries());
        assertEquals(1, stats.getTtlEntries());
    }

    @Test
    void testTypeCasting() {
        database.create("string", "hello");
        database.create("integer", 42);
        database.create("double", 3.14);
        
        String stringValue = database.read("string", String.class);
        assertEquals("hello", stringValue);
        
        Integer intValue = database.read("integer", Integer.class);
        assertEquals(Integer.valueOf(42), intValue);
        
        Double doubleValue = database.read("double", Double.class);
        assertEquals(Double.valueOf(3.14), doubleValue);
        
        // Test wrong type
        String wrongType = database.read("integer", String.class);
        assertNull(wrongType);
    }
}
