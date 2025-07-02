package ratelimiter;

public interface RateLimiter {
    boolean allowRequest(String clientId);

    void setRateLimit(String clientId, int capacity, int rate);
}
