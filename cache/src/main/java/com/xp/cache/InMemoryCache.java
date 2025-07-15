package com.xp.cache;

import java.util.HashMap;
import java.util.Map;

import com.xp.policy.EvictionPolicy;

public class InMemoryCache<K, V> implements Cache<K, V> {
    private final Map<K, V> cacheStorage;
    private final int capacity;
    private final EvictionPolicy<K, V> evictionPolicy;

    public InMemoryCache(int capacity, EvictionPolicy<K, V> evictionPolicy) {
        this.capacity = capacity;
        this.cacheStorage = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }
    
    @Override
    public V get(K key) {
        evictionPolicy.recordAccess(key);
        return cacheStorage.get(key);
    }
    
    @Override
    public void put(K key, V value) {
        if(cacheStorage.size() >= capacity) {
            K keyToEvict = evictionPolicy.evictKey();
            if (keyToEvict != null) {
                cacheStorage.remove(keyToEvict);
            }
        }
        cacheStorage.put(key, value);
        evictionPolicy.recordAccess(key);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getCurrentSize() {
        return cacheStorage.size();
    }
}
