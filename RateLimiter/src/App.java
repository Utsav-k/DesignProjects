import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ratelimiter.RateLimiter;
import ratelimiter.RateLimiterStrategy;
import ratelimiter.factory.RateLimiterFactory;

public class App {
    public static void main(String[] args) throws InterruptedException {

        testTokenBucketStrategy();
        testSlidingWindowStrategy();
    }

    private static void testSlidingWindowStrategy() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiterFactory.createRateLimiter(RateLimiterStrategy.SLIDING_WINDOW);

        rateLimiter.setRateLimit("api_user", 5, 2);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.scheduleAtFixedRate(() -> {
            boolean allowed = rateLimiter.allowRequest("api_user");
            System.out.println((allowed ? "ALLOWED" : "DENIED"));
        }, 
        0, 
        20, 
        TimeUnit.MILLISECONDS); 

        Thread.sleep(10000);
        scheduler.shutdown();
    }

    private static void testTokenBucketStrategy() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiterFactory.createRateLimiter(RateLimiterStrategy.TOKEN_BUCKET);

        rateLimiter.setRateLimit("api_user", 10, 10);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.scheduleAtFixedRate(() -> {
            boolean allowed = rateLimiter.allowRequest("api_user");
            System.out.println((allowed ? "ALLOWED" : "DENIED"));
        }, 
        0, 
        100, 
        TimeUnit.MILLISECONDS); 

        Thread.sleep(2000);
        scheduler.shutdown();
    }
}
