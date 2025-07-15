package com.xp.policy;

import java.util.LinkedHashSet;
import java.util.Set;

public class LRUEviction<K, V> implements EvictionPolicy<K, V> {
    
    Set<K> accessOrder = new LinkedHashSet<>();

    @Override
    public K evictKey() {
        if (accessOrder.isEmpty()) {
            return null; 
        }
        K keyToEvict = accessOrder.iterator().next();
        accessOrder.remove(keyToEvict);
        return keyToEvict;
    }

    @Override
    public void recordAccess(K key) {
        if(accessOrder.contains(key)) {
            accessOrder.remove(key);
        }
        accessOrder.add(key);
    }
}