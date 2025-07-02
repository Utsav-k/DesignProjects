package ratelimiter.tokenbucket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ratelimiter.RateLimiter;

public class TokenBucketRateLimiter implements RateLimiter {

    private final Map<String, TokenBucket> clientBuckets = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String clientId) {
        TokenBucket tokenBucket = clientBuckets.get(clientId);
        return null != tokenBucket && tokenBucket.consumeToken();
    }

    @Override
    public void setRateLimit(String clientId, int maxTokens, int rate) {
        clientBuckets.put(clientId, new TokenBucket(maxTokens, rate));
    }
}
