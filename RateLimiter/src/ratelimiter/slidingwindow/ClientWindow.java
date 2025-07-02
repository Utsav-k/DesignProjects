package ratelimiter.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

public class ClientWindow {
    private int capacity;
    private long windowSize;
    private Deque<Long> requestTimeStamps = new LinkedList<>();

    public ClientWindow(int capacity, int windowSize) {
        this.capacity = capacity;
        this.windowSize = (long) windowSize * 1000;
    }

    public int getCapacity() {
        return capacity;
    }

    public long getWindowSize() {
        return windowSize;
    }

    public Deque<Long> getRequestTimeStamps() {
        return requestTimeStamps;
    }
}
