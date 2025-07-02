package ratelimiter.slidingwindow;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ratelimiter.RateLimiter;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final Map<String, ClientWindow> clientWindows = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String clientId) {
        ClientWindow window = clientWindows.get(clientId);
        if(null == window) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        long windowStartTime = currentTime - window.getWindowSize();

        // Synchronized access to time deque, so other threads can't change it
        synchronized (window.getRequestTimeStamps()) {
            Deque<Long> deque = window.getRequestTimeStamps();
            // Remove all the timeStamps before the windowStartTime
            while(!deque.isEmpty() && deque.peekFirst() < windowStartTime) {
                deque.removeFirst();
            }
            if(deque.size() < window.getCapacity()) {
                deque.add(currentTime);
                return true;
            }
            return false;
        }
    }

    @Override
    public void setRateLimit(String clientId, int capacity, int windowSize) {
        clientWindows.put(clientId, new ClientWindow(capacity, windowSize));
    }

}
