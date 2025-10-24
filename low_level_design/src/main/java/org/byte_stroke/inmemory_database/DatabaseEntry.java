package org.byte_stroke.inmemory_database;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a database entry with value, metadata, and TTL support
 */
public class DatabaseEntry implements Serializable {
    private final String key;
    private final Object value;
    private final Instant createdAt;
    private final Instant expiresAt;
    private final Map<String, Object> metadata;
    private volatile Instant lastAccessedAt;

    public DatabaseEntry(String key, Object value) {
        this(key, value, null, null);
    }

    public DatabaseEntry(String key, Object value, Long ttlSeconds) {
        this(key, value, ttlSeconds, null);
    }

    public DatabaseEntry(String key, Object value, Long ttlSeconds, Map<String, Object> metadata) {
        this.key = key;
        this.value = value;
        this.createdAt = Instant.now();
        this.expiresAt = ttlSeconds != null ? createdAt.plusSeconds(ttlSeconds) : null;
        this.metadata = metadata;
        this.lastAccessedAt = createdAt;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        updateLastAccessed();
        return value;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public Instant getLastAccessedAt() {
        return lastAccessedAt;
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    public long getRemainingTTL() {
        if (expiresAt == null) {
            return -1; // No TTL
        }
        long remaining = expiresAt.toEpochMilli() - Instant.now().toEpochMilli();
        return Math.max(0, remaining);
    }

    private void updateLastAccessed() {
        this.lastAccessedAt = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseEntry that = (DatabaseEntry) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "DatabaseEntry{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", isExpired=" + isExpired() +
                '}';
    }
}
