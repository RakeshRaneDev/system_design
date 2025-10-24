package org.byte_stroke.inmemory_database;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages TTL (Time To Live) for database entries
 */
public class TTLManager {
    private final Map<String, DatabaseEntry> data;
    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean isRunning;
    private final long cleanupIntervalSeconds;

    public TTLManager(Map<String, DatabaseEntry> data) {
        this(data, 60); // Default cleanup every 60 seconds
    }

    public TTLManager(Map<String, DatabaseEntry> data, long cleanupIntervalSeconds) {
        this.data = data;
        this.cleanupIntervalSeconds = cleanupIntervalSeconds;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "TTL-Cleanup");
            t.setDaemon(true);
            return t;
        });
        this.isRunning = new AtomicBoolean(false);
    }

    /**
     * Starts the TTL cleanup process
     */
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            scheduler.scheduleWithFixedDelay(
                    this::cleanupExpiredEntries,
                    cleanupIntervalSeconds,
                    cleanupIntervalSeconds,
                    TimeUnit.SECONDS
            );
        }
    }

    /**
     * Stops the TTL cleanup process
     */
    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Manually triggers cleanup of expired entries
     */
    public int cleanupExpiredEntries() {
        int removedCount = 0;
        Instant now = Instant.now();
        
        for (Map.Entry<String, DatabaseEntry> entry : data.entrySet()) {
            DatabaseEntry dbEntry = entry.getValue();
            if (dbEntry.isExpired()) {
                data.remove(entry.getKey());
                removedCount++;
            }
        }
        
        return removedCount;
    }

    /**
     * Checks if an entry is expired without removing it
     */
    public boolean isEntryExpired(String key) {
        DatabaseEntry entry = data.get(key);
        return entry != null && entry.isExpired();
    }

    /**
     * Gets the remaining TTL for an entry
     */
    public long getRemainingTTL(String key) {
        DatabaseEntry entry = data.get(key);
        return entry != null ? entry.getRemainingTTL() : -1;
    }

    /**
     * Extends the TTL of an existing entry
     */
    public boolean extendTTL(String key, long additionalSeconds) {
        DatabaseEntry entry = data.get(key);
        if (entry != null && !entry.isExpired()) {
            // Create a new entry with extended TTL
            DatabaseEntry newEntry = new DatabaseEntry(
                    entry.getKey(),
                    entry.getValue(),
                    entry.getExpiresAt() != null ? 
                        entry.getExpiresAt().plusSeconds(additionalSeconds).getEpochSecond() - entry.getCreatedAt().getEpochSecond() :
                        additionalSeconds,
                    entry.getMetadata()
            );
            data.put(key, newEntry);
            return true;
        }
        return false;
    }

    /**
     * Sets TTL for an existing entry
     */
    public boolean setTTL(String key, long ttlSeconds) {
        DatabaseEntry entry = data.get(key);
        if (entry != null && !entry.isExpired()) {
            DatabaseEntry newEntry = new DatabaseEntry(
                    entry.getKey(),
                    entry.getValue(),
                    ttlSeconds,
                    entry.getMetadata()
            );
            data.put(key, newEntry);
            return true;
        }
        return false;
    }

    /**
     * Removes TTL from an entry (makes it permanent)
     */
    public boolean removeTTL(String key) {
        DatabaseEntry entry = data.get(key);
        if (entry != null && !entry.isExpired()) {
            DatabaseEntry newEntry = new DatabaseEntry(
                    entry.getKey(),
                    entry.getValue(),
                    null,
                    entry.getMetadata()
            );
            data.put(key, newEntry);
            return true;
        }
        return false;
    }

    /**
     * Gets statistics about TTL entries
     */
    public TTLStats getTTLStats() {
        int totalEntries = data.size();
        int expiredEntries = 0;
        int ttlEntries = 0;
        long totalRemainingTTL = 0;

        for (DatabaseEntry entry : data.values()) {
            if (entry.isExpired()) {
                expiredEntries++;
            } else if (entry.getExpiresAt() != null) {
                ttlEntries++;
                totalRemainingTTL += entry.getRemainingTTL();
            }
        }

        return new TTLStats(totalEntries, expiredEntries, ttlEntries, totalRemainingTTL);
    }

    public static class TTLStats {
        private final int totalEntries;
        private final int expiredEntries;
        private final int ttlEntries;
        private final long totalRemainingTTL;

        public TTLStats(int totalEntries, int expiredEntries, int ttlEntries, long totalRemainingTTL) {
            this.totalEntries = totalEntries;
            this.expiredEntries = expiredEntries;
            this.ttlEntries = ttlEntries;
            this.totalRemainingTTL = totalRemainingTTL;
        }

        public int getTotalEntries() { return totalEntries; }
        public int getExpiredEntries() { return expiredEntries; }
        public int getTtlEntries() { return ttlEntries; }
        public long getTotalRemainingTTL() { return totalRemainingTTL; }

        @Override
        public String toString() {
            return String.format("TTLStats{total=%d, expired=%d, ttl=%d, avgRemainingTTL=%dms}",
                    totalEntries, expiredEntries, ttlEntries, 
                    ttlEntries > 0 ? totalRemainingTTL / ttlEntries : 0);
        }
    }
}
