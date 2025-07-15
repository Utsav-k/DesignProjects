package com.xp.policy;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUEviction<K, V> implements EvictionPolicy<K, V> {

    private final Map<K, Integer> freqMap = new HashMap<>();
    private final Map<Integer, LinkedHashSet<K>> freqToKeys = new HashMap<>();
    private int minFreq = 1;

    @Override
    public void recordAccess(K key) {
        int oldFreq = freqMap.getOrDefault(key, 0);
        int newFreq = oldFreq + 1;

        freqMap.put(key, newFreq);

        // Remove from old freq bucket
        if (oldFreq > 0) {
            LinkedHashSet<K> oldSet = freqToKeys.get(oldFreq);
            oldSet.remove(key);
            if (oldSet.isEmpty()) {
                freqToKeys.remove(oldFreq);
                if (minFreq == oldFreq) minFreq = newFreq;
            }
        }

        // Add to new freq bucket
        freqToKeys.computeIfAbsent(newFreq, f -> new LinkedHashSet<>()).add(key);
    }

    @Override
    public K evictKey() {
        LinkedHashSet<K> keys = freqToKeys.get(minFreq);
        if (keys == null || keys.isEmpty()) return null;

        K evict = keys.iterator().next();
        keys.remove(evict);

        if (keys.isEmpty()) freqToKeys.remove(minFreq);
        freqMap.remove(evict);

        return evict;
    }
}
