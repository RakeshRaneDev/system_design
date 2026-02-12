package org.byte_coding_prep.inmemory_database_lld;

public class Item {
    private String field;
    private String value;
    private int timestamp;
    private int ttl; // 0 means infinite lifetime

    public Item(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public Item(String field, String value, int timestamp, int ttl) {
        this.field = field;
        this.value = value;
        this.timestamp = timestamp;
        this.ttl = ttl;
    }

    public Item(Item other) {
        this.field = other.field;
        this.value = other.value;
        this.timestamp = other.timestamp;
        this.ttl = other.ttl;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getTtl() {
        return ttl;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public boolean isAliveAt(int time) {
        return ttl == 0 || time <= timestamp + ttl;
    }
}
