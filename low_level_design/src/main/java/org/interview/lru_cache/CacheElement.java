package org.interview.lru_cache;

public class CacheElement <K, V>{
    private K key;
    private V value;

    public CacheElement(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
