package ratelimiter.tokenbucket;

public class TokenBucket {

    private int maxTokens;
    private int remainingTokens;
    private int rate;
    private Long lastRefillTime;

    public TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.remainingTokens = maxTokens;  // start full
        this.rate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean consumeToken() {
        Long curTime = System.currentTimeMillis();
        int timeSinceLastRefill = (int)(curTime - lastRefillTime)/1000;
        remainingTokens = Math.min(maxTokens, remainingTokens + timeSinceLastRefill * rate);
        lastRefillTime = curTime;
        if(remainingTokens > 0) {
            remainingTokens--;
            return true;
        }
        return false;
    }
}
