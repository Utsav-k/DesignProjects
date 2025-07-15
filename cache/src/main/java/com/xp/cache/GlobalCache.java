package com.xp.cache;

import com.xp.policy.EvictionPolicy;

// Global Cache has to be a singleton
public class GlobalCache {
    private static InMemoryCache<Integer, String> instance;

    public static synchronized InMemoryCache<Integer, String> getInstance(final int capacity, final EvictionPolicy<Integer, String> evictionPolicy) {
        if(instance == null) {
            instance = new InMemoryCache<>(capacity, evictionPolicy);
        }
        return instance;
    }
}
