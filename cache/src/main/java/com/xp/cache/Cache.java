package com.xp.cache;

public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    int getCapacity();
    int getCurrentSize();
}
