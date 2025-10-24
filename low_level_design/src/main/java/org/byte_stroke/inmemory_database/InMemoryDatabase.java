package org.byte_stroke.inmemory_database;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Thread-safe in-memory database with CRUD operations, advanced filtering, TTL support, and backup functionality
 */
public class InMemoryDatabase {
    private final Map<String, DatabaseEntry> data;
    private final ReadWriteLock lock;
    public final TTLManager ttlManager;
    private final BackupManager backupManager;
    private final String name;
    private volatile boolean isStarted;

    public InMemoryDatabase() {
        this("default");
    }

    public InMemoryDatabase(String name) {
        this(name, "./backups");
    }

    public InMemoryDatabase(String name, String backupDirectory) {
        this.name = name;
        this.data = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
        this.ttlManager = new TTLManager(data);
        this.backupManager = new BackupManager(backupDirectory, true);
        this.isStarted = false;
    }

    /**
     * Starts the database services (TTL cleanup, etc.)
     */
    public void start() {
        if (!isStarted) {
            ttlManager.start();
            isStarted = true;
        }
    }

    /**
     * Stops the database services
     */
    public void stop() {
        if (isStarted) {
            ttlManager.stop();
            isStarted = false;
        }
    }

    // ========== CRUD Operations ==========

    /**
     * Creates a new entry in the database
     */
    public boolean create(String key, Object value) {
        return create(key, value, null, null);
    }

    /**
     * Creates a new entry with TTL
     */
    public boolean create(String key, Object value, Long ttlSeconds) {
        return create(key, value, ttlSeconds, null);
    }

    /**
     * Creates a new entry with TTL and metadata
     */
    public boolean create(String key, Object value, Long ttlSeconds, Map<String, Object> metadata) {
        if (key == null || value == null) {
            return false;
        }

        lock.writeLock().lock();
        try {
            if (data.containsKey(key)) {
                return false; // Key already exists
            }
            
            DatabaseEntry entry = new DatabaseEntry(key, value, ttlSeconds, metadata);
            data.put(key, entry);
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Creates or updates an entry (upsert)
     */
    public boolean upsert(String key, Object value) {
        return upsert(key, value, null, null);
    }

    /**
     * Creates or updates an entry with TTL
     */
    public boolean upsert(String key, Object value, Long ttlSeconds) {
        return upsert(key, value, ttlSeconds, null);
    }

    /**
     * Creates or updates an entry with TTL and metadata
     */
    public boolean upsert(String key, Object value, Long ttlSeconds, Map<String, Object> metadata) {
        if (key == null || value == null) {
            return false;
        }

        lock.writeLock().lock();
        try {
            DatabaseEntry entry = new DatabaseEntry(key, value, ttlSeconds, metadata);
            data.put(key, entry);
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Reads a value by key
     */
    public Object read(String key) {
        if (key == null) {
            return null;
        }

        lock.readLock().lock();
        try {
            DatabaseEntry entry = data.get(key);
            if (entry == null || entry.isExpired()) {
                if (entry != null && entry.isExpired()) {
                    data.remove(key); // Clean up expired entry
                }
                return null;
            }
            return entry.getValue();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Reads a value with type casting
     */
    @SuppressWarnings("unchecked")
    public <T> T read(String key, Class<T> type) {
        Object value = read(key);
        if (value != null && type.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    /**
     * Reads the full database entry
     */
    public DatabaseEntry readEntry(String key) {
        if (key == null) {
            return null;
        }

        lock.readLock().lock();
        try {
            DatabaseEntry entry = data.get(key);
            if (entry == null || entry.isExpired()) {
                if (entry != null && entry.isExpired()) {
                    data.remove(key); // Clean up expired entry
                }
                return null;
            }
            return entry;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Updates an existing entry
     */
    public boolean update(String key, Object value) {
        return update(key, value, null, null);
    }

    /**
     * Updates an existing entry with new TTL
     */
    public boolean update(String key, Object value, Long ttlSeconds) {
        return update(key, value, ttlSeconds, null);
    }

    /**
     * Updates an existing entry with new TTL and metadata
     */
    public boolean update(String key, Object value, Long ttlSeconds, Map<String, Object> metadata) {
        if (key == null || value == null) {
            return false;
        }

        lock.writeLock().lock();
        try {
            DatabaseEntry existingEntry = data.get(key);
            if (existingEntry == null || existingEntry.isExpired()) {
                return false; // Key doesn't exist or is expired
            }

            DatabaseEntry newEntry = new DatabaseEntry(key, value, ttlSeconds, metadata);
            data.put(key, newEntry);
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Deletes an entry by key
     */
    public boolean delete(String key) {
        if (key == null) {
            return false;
        }

        lock.writeLock().lock();
        try {
            return data.remove(key) != null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Deletes multiple entries by keys
     */
    public int deleteAll(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return 0;
        }

        lock.writeLock().lock();
        try {
            int deletedCount = 0;
            for (String key : keys) {
                if (data.remove(key) != null) {
                    deletedCount++;
                }
            }
            return deletedCount;
        } finally {
            lock.writeLock().unlock();
        }
    }

    // ========== Advanced Filtering and Search ==========

    /**
     * Finds entries matching a single condition
     */
    public List<DatabaseEntry> find(QueryCondition condition) {
        return find(Collections.singletonList(condition), null, null);
    }

    /**
     * Finds entries matching multiple conditions (AND operation)
     */
    public List<DatabaseEntry> find(List<QueryCondition> conditions) {
        return find(conditions, null, null);
    }

    /**
     * Finds entries with custom predicate
     */
    public List<DatabaseEntry> find(Predicate<DatabaseEntry> predicate) {
        return find(Collections.singletonList(new QueryCondition(predicate)), null, null);
    }

    /**
     * Finds entries with pagination
     */
    public List<DatabaseEntry> find(List<QueryCondition> conditions, Integer limit, Integer offset) {
        lock.readLock().lock();
        try {
            Predicate<DatabaseEntry> combinedPredicate = conditions.stream()
                    .map(QueryCondition::getPredicate)
                    .reduce((p1, p2) -> p1.and(p2))
                    .orElse(entry -> true);

            List<DatabaseEntry> results = data.values().stream()
                    .filter(entry -> !entry.isExpired())
                    .filter(combinedPredicate)
                    .collect(Collectors.toList());

            if (offset != null && offset > 0) {
                results = results.stream()
                        .skip(offset)
                        .collect(Collectors.toList());
            }

            if (limit != null && limit > 0) {
                results = results.stream()
                        .limit(limit)
                        .collect(Collectors.toList());
            }

            return results;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Searches entries by value content (case-insensitive)
     */
    public List<DatabaseEntry> search(String searchTerm) {
        return find(new QueryCondition("value", searchTerm, QueryCondition.ComparisonOperator.CONTAINS));
    }

    /**
     * Searches entries by key pattern
     */
    public List<DatabaseEntry> searchByKey(String keyPattern) {
        return find(new QueryCondition("key", keyPattern, QueryCondition.ComparisonOperator.CONTAINS));
    }

    /**
     * Finds entries created within a time range
     */
    public List<DatabaseEntry> findByCreatedAtRange(Instant startTime, Instant endTime) {
        return find(entry -> {
            Instant createdAt = entry.getCreatedAt();
            return createdAt.isAfter(startTime) && createdAt.isBefore(endTime);
        });
    }

    /**
     * Finds entries that will expire within a time range
     */
    public List<DatabaseEntry> findByExpirationRange(Instant startTime, Instant endTime) {
        return find(entry -> {
            Instant expiresAt = entry.getExpiresAt();
            return expiresAt != null && expiresAt.isAfter(startTime) && expiresAt.isBefore(endTime);
        });
    }

    // ========== TTL Operations ==========

    /**
     * Sets TTL for an existing entry
     */
    public boolean setTTL(String key, long ttlSeconds) {
        return ttlManager.setTTL(key, ttlSeconds);
    }

    /**
     * Extends TTL for an existing entry
     */
    public boolean extendTTL(String key, long additionalSeconds) {
        return ttlManager.extendTTL(key, additionalSeconds);
    }

    /**
     * Removes TTL from an entry (makes it permanent)
     */
    public boolean removeTTL(String key) {
        return ttlManager.removeTTL(key);
    }

    /**
     * Gets remaining TTL for an entry
     */
    public long getRemainingTTL(String key) {
        return ttlManager.getRemainingTTL(key);
    }

    /**
     * Checks if an entry is expired
     */
    public boolean isExpired(String key) {
        return ttlManager.isEntryExpired(key);
    }

    // ========== Backup Operations ==========

    /**
     * Creates a backup of the database
     */
    public String createBackup() {
        lock.readLock().lock();
        try {
            return backupManager.createBackup(new HashMap<>(data));
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Restores the database from a backup
     */
    public boolean restoreFromBackup(String backupPath) {
        lock.writeLock().lock();
        try {
            Map<String, DatabaseEntry> backupData = backupManager.restoreFromBackup(backupPath);
            data.clear();
            data.putAll(backupData);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Lists available backups
     */
    public String[] listBackups() {
        return backupManager.listBackups();
    }

    /**
     * Cleans up old backups
     */
    public void cleanupOldBackups(int keepCount) {
        backupManager.cleanupOldBackups(keepCount);
    }

    // ========== Utility Operations ==========

    /**
     * Gets the size of the database
     */
    public int size() {
        lock.readLock().lock();
        try {
            return (int) data.values().stream().filter(entry -> !entry.isExpired()).count();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Checks if the database is empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Checks if a key exists
     */
    public boolean exists(String key) {
        return read(key) != null;
    }

    /**
     * Gets all keys
     */
    public Set<String> keys() {
        lock.readLock().lock();
        try {
            return data.values().stream()
                    .filter(entry -> !entry.isExpired())
                    .map(DatabaseEntry::getKey)
                    .collect(Collectors.toSet());
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Gets all values
     */
    public List<Object> values() {
        lock.readLock().lock();
        try {
            return data.values().stream()
                    .filter(entry -> !entry.isExpired())
                    .map(DatabaseEntry::getValue)
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Clears all entries
     */
    public void clear() {
        lock.writeLock().lock();
        try {
            data.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Gets database statistics
     */
    public DatabaseStats getStats() {
        lock.readLock().lock();
        try {
            TTLManager.TTLStats ttlStats = ttlManager.getTTLStats();
            return new DatabaseStats(
                    name,
                    ttlStats.getTotalEntries(),
                    ttlStats.getExpiredEntries(),
                    ttlStats.getTtlEntries(),
                    isStarted
            );
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Database statistics
     */
    public static class DatabaseStats {
        private final String name;
        private final int totalEntries;
        private final int expiredEntries;
        private final int ttlEntries;
        private final boolean isStarted;

        public DatabaseStats(String name, int totalEntries, int expiredEntries, int ttlEntries, boolean isStarted) {
            this.name = name;
            this.totalEntries = totalEntries;
            this.expiredEntries = expiredEntries;
            this.ttlEntries = ttlEntries;
            this.isStarted = isStarted;
        }

        public String getName() { return name; }
        public int getTotalEntries() { return totalEntries; }
        public int getExpiredEntries() { return expiredEntries; }
        public int getTtlEntries() { return ttlEntries; }
        public boolean isStarted() { return isStarted; }

        @Override
        public String toString() {
            return String.format("DatabaseStats{name='%s', total=%d, expired=%d, ttl=%d, started=%s}",
                    name, totalEntries, expiredEntries, ttlEntries, isStarted);
        }
    }
}
