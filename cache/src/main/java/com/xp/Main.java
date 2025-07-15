package com.xp;

import java.util.Random;

import com.xp.api.API;
// import com.xp.cache.GlobalCache;
import com.xp.cache.InMemoryCache;
import com.xp.policy.EvictionPolicy;
import com.xp.policy.LFUEviction;
import com.xp.policy.LRUEviction;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        API api = new API();
        Random random = new Random();

        EvictionPolicy<Integer, String> lruEviction = new LRUEviction<>();
        EvictionPolicy<Integer, String> lfuEviction = new LFUEviction<>();

        InMemoryCache<Integer, String> cache = new InMemoryCache<>(5, lfuEviction);
        
        
        // InMemoryCache<Integer, String> cache = GlobalCache.getInstance(10, evictionPolicy);
        // cache = GlobalCache.getInstance(10, evictionPolicy); // This wouldn't work because global cache can only be initialized once
        
        int cacheHit = 0;
        for (int i = 0; i < 50; i++) {
            int input = random.nextInt(10);
            if(cache.get(input) == null) {
                String response = api.getResponse(input);
                cache.put(input, response);
                System.out.println("Cache Miss: " + response);
            } else {
                cacheHit++;
                System.out.println("Cache Hit: " + cache.get(input));
            }
            Thread.sleep(100);
        }

        System.out.println("Total Cache Hits: " + cacheHit);
    }
    
}