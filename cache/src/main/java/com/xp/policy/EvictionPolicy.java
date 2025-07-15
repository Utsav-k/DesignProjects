package com.xp.policy;

public interface EvictionPolicy<K,V> {
    K evictKey();
    void recordAccess(K key);
}
