package ratelimiter.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ratelimiter.RateLimiter;
import ratelimiter.RateLimiterStrategy;
import ratelimiter.slidingwindow.SlidingWindowRateLimiter;
import ratelimiter.tokenbucket.TokenBucketRateLimiter;

public class RateLimiterFactory {

    // Cache for singleton instances of each rate limiter type
    private static final Map<RateLimiterStrategy, RateLimiter> instances = new ConcurrentHashMap<>();

    public static RateLimiter createRateLimiter(RateLimiterStrategy strategyType) {
        // Use computeIfAbsent to create the instance only if it doesn't already exist
        return instances.computeIfAbsent(strategyType, type -> {
            switch (type) {
                case TOKEN_BUCKET:
                    System.out.println("Creating new TokenBucketRateLimiter instance.");
                    return new TokenBucketRateLimiter();
                case SLIDING_WINDOW:
                    System.out.println("Creating new SlidingWindowRateLimiter instance.");
                    return new SlidingWindowRateLimiter();
                default:
                    throw new IllegalArgumentException("Unknown rate limit strategy: " + type);
            }
        });
    }

    // public static RateLimiter createRateLimiter(RateLimiterStrategy strategyType) {
    //     switch (strategyType) {
    //         case TOKEN_BUCKET:
    //             return new TokenBucketRateLimiter();
    //         case SLIDING_WINDOW:
    //             return null;
    //         default:
    //             throw new IllegalArgumentException("Unknown rate limit strategy: " + strategyType);
    //     }
    // }
}
