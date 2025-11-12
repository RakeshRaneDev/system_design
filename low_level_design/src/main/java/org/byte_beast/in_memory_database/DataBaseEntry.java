package org.byte_beast.in_memory_database;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class DataBaseEntry implements Serializable {
    private final String key;
    private  final Object value;
    private final Instant createdAt;
    private final Instant expireAt;
    private volatile Instant lastAccessedAt;

    private Map<String, Object> MataData;

    public DataBaseEntry(String key, Object value) {
    this(key, value, null, null);
    }

    public DataBaseEntry(String key, Object value, Long ttl) {
        this(key, value, ttl, null);
    }


    public DataBaseEntry(String key, Object value, Long ttl, Map<String, Object> mataData) {
        this.key = key;
        this.value = value;
        this.createdAt = Instant.now();
        this.expireAt = ttl !=null ? Instant.now().plusSeconds(ttl): null;
        this.lastAccessedAt = createdAt;
        MataData = mataData;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpireAt() {
        return expireAt;
    }

    public Instant getLastAccessedAt() {
        return lastAccessedAt;
    }

    public Map<String, Object> getMataData() {
        return MataData;
    }

    /**
     *
     * @return if data entry expired or not;
     */
    public  boolean isExpired(){
        return expireAt !=null && Instant.now().isAfter(expireAt);
    }

    /**
     *
     * @return remaining ttl time
     */
    public Long getRemainingTtl(){
        if(expireAt==null){
            return -1l;
        }
        long remainingTtl = expireAt.toEpochMilli() - Instant.now().toEpochMilli();
        return Math.max(0, remainingTtl);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBaseEntry that = (DataBaseEntry) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DataBaseEntry.class.getSimpleName() + "[", "]")
                .add("key='" + key + "'")
                .add("value=" + value)
                .add("createdAt=" + createdAt)
                .add("expireAt=" + expireAt)
                .add("lastAccessedAt=" + lastAccessedAt)
                .add("MataData=" + MataData)
                .toString();
    }
}
