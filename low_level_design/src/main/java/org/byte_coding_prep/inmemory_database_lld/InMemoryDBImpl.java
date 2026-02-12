package org.byte_coding_prep.inmemory_database_lld;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryDBImpl {
    private final ReentrantLock lock = new ReentrantLock();
    private final Map<String, List<Item>> itemList = new HashMap<>();

    public InMemoryDBImpl() {}

    // ========== BASIC SET ==========
    public void set(String key, String field, String value) {
        lock.lock();
        try {
            List<Item> items = itemList.getOrDefault(key, new ArrayList<>());
            items.add(new Item(field, value));
            itemList.put(key, items);
        } finally {
            lock.unlock();
        }
    }

    // ========== BASIC GET ==========
    public String get(String key, String field) {
        lock.lock();
        try {
            List<Item> items = itemList.get(key);
            if (items == null) return null;

            for (Item item : items) {
                if (item.getField().equals(field)) {
                    return item.getValue();
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    // ========== BASIC DELETE ==========
    public void delete(String key, String field) {
        lock.lock();
        try {
            List<Item> items = itemList.get(key);
            if (items == null) return;

            items.removeIf(item -> item.getField().equals(field));
        } finally {
            lock.unlock();
        }
    }

    // ========== SET WITH TIMESTAMP ==========
    public void setAt(String key, String field, String value, int timestamp) {
        lock.lock();
        try {
            List<Item> items = itemList.getOrDefault(key, new ArrayList<>());

            for (Item item : items) {
                if (item.getField().equals(field)) {
                    item.setValue(value);
                    item.setTimestamp(timestamp);
                    return;
                }
            }

            items.add(new Item(field, value, timestamp, 0));
            itemList.put(key, items);
        } finally {
            lock.unlock();
        }
    }

    // ========== SET WITH TTL ==========
    public void setAtWithTtl(String key, String field, String value, int timestamp, int ttl) {
        lock.lock();
        try {
            List<Item> items = itemList.getOrDefault(key, new ArrayList<>());

            for (Item item : items) {
                if (item.getField().equals(field)) {
                    item.setValue(value);
                    item.setTimestamp(timestamp);
                    item.setTtl(ttl);
                    return;
                }
            }

            items.add(new Item(field, value, timestamp, ttl));
            itemList.put(key, items);
        } finally {
            lock.unlock();
        }
    }

    // ========== GET WITH TIMESTAMP (RESPECT TTL) ==========
    public String getAt(String key, String field, int timestamp) {
        lock.lock();
        try {
            List<Item> items = itemList.get(key);
            if (items == null) return null;

            for (Item item : items) {
                if (item.getField().equals(field) && item.isAliveAt(timestamp)) {
                    return item.getValue();
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    // ========== DELETE WITH TIMESTAMP ==========
    public boolean deleteAt(String key, String field, int timestamp) {
        lock.lock();
        try {
            List<Item> items = itemList.get(key);
            if (items == null) return false;

            Iterator<Item> it = items.iterator();
            while (it.hasNext()) {
                Item item = it.next();
                if (item.getField().equals(field)) {
                    it.remove();
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // ========== SCAN ==========
    public List<String> scanAt(String key, int timestamp) {
        lock.lock();
        try {
            List<String> result = new ArrayList<>();
            List<Item> items = itemList.get(key);
            if (items == null) return result;

            for (Item item : items) {
                if (item.isAliveAt(timestamp)) {
                    result.add(item.getField() + "(" + item.getValue() + ")");
                }
            }
            return result;
        } finally {
            lock.unlock();
        }
    }

    // ========== SCAN WITH PREFIX ==========
    public List<String> scanByPrefixAt(String key, String prefix, int timestamp) {
        lock.lock();
        try {
            List<String> result = new ArrayList<>();
            List<Item> items = itemList.get(key);
            if (items == null) return result;

            for (Item item : items) {
                if (item.getField().startsWith(prefix) && item.isAliveAt(timestamp)) {
                    result.add(item.getField() + "(" + item.getValue() + ")");
                }
            }
            return result;
        } finally {
            lock.unlock();
        }
    }

    // ========== CREATE A DEEP BACKUP SNAPSHOT ==========
    public BackupSnapshot backup() {
        lock.lock();
        try {
            Map<String, List<Item>> deepCopy = new HashMap<>();

            for (Map.Entry<String, List<Item>> entry : itemList.entrySet()) {
                List<Item> copyList = new ArrayList<>();
                for (Item item : entry.getValue()) {
                    copyList.add(new Item(item)); // deep clone item
                }
                deepCopy.put(entry.getKey(), copyList);
            }

            return new BackupSnapshot(deepCopy);

        } finally {
            lock.unlock();
        }
    }

    // ========== RESTORE FROM BACKUP ==========
    public void restore(BackupSnapshot snapshot) {
        lock.lock();
        try {
            itemList.clear();

            for (Map.Entry<String, List<Item>> entry : snapshot.getSnapshot().entrySet()) {
                List<Item> newList = new ArrayList<>();
                for (Item item : entry.getValue()) {
                    newList.add(new Item(item)); // deep clone again
                }
                itemList.put(entry.getKey(), newList);
            }

        } finally {
            lock.unlock();
        }
    }
}
