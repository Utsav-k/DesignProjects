1. Sequential Thread Execution
Problem: Make threads execute in a specific order (e.g., T1 → T2 → T3).
Key Concept: Thread coordination using join(), wait()/notify(), or CompletableFuture.
Code Skeleton:

java
Thread t1 = new Thread(() -> System.out.println("T1"));
Thread t2 = new Thread(() -> System.out.println("T2"));
Thread t3 = new Thread(() -> System.out.println("T3"));
// Modify to enforce order.


2. Thread-Safe Singleton
Problem: Implement the singleton pattern with lazy initialization that works in a multithreaded environment.
Key Concept: Double-checked locking, volatile, or static holder pattern.
Hint:

java
public class Singleton {
    private static volatile Singleton instance;
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
3. Print Numbers Alternately
Problem: Two threads print numbers 1-10 alternately (ThreadA: 1, ThreadB: 2, ThreadA: 3, etc.).
Key Concept: Synchronization with wait()/notify().
Hint: Use a shared lock and a flag to toggle between threads.

4. Producer-Consumer with a Bounded Buffer
Problem: Implement a producer-consumer system where producers add items to a fixed-size queue and consumers remove them.
Key Concept: BlockingQueue, wait()/notify(), or ReentrantLock with conditions.
Bonus: Add a poison pill to stop consumers gracefully.

5. Dining Philosophers
Problem: Simulate the dining philosophers problem with 5 philosophers and avoid deadlocks.
Key Concept: Deadlock prevention (resource ordering, timeouts).
Hint: Assign a global order to chopsticks (locks) to avoid circular waits.

6. Rate Limiter
Problem: Implement a thread-safe rate limiter that allows N requests per second.
Key Concept: Semaphore, token bucket, or sliding window algorithm.
Code Skeleton:

java
class RateLimiter {
    private Semaphore semaphore;
    public RateLimiter(int permits) { 
        this.semaphore = new Semaphore(permits);
    }
    public void execute(Runnable task) throws InterruptedException {
        semaphore.acquire();
        try { task.run(); } 
        finally { semaphore.release(); }
    }
}
7. Concurrent Counter
Problem: Create a counter shared by multiple threads that increments safely.
Key Concept: AtomicInteger, synchronized, or ReentrantLock.
Bonus: Compare performance of synchronized vs AtomicInteger.

8. Thread Pool with Task Queue
Problem: Implement a simple thread pool with a task queue.
Key Concept: ExecutorService, worker threads, and BlockingQueue.
Hint: Use LinkedBlockingQueue and a fixed set of worker threads.

9. Read-Write Lock
Problem: Simulate a cache with multiple readers and single writer.
Key Concept: ReentrantReadWriteLock or StampedLock.
Code Skeleton:

java
class ThreadSafeCache {
    private Map<String, String> cache = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    public String get(String key) {
        lock.readLock().lock();
        try { return cache.get(key); } 
        finally { lock.readLock().unlock(); }
    }
    public void put(String key, String value) {
        lock.writeLock().lock();
        try { cache.put(key, value); } 
        finally { lock.writeLock().unlock(); }
    }
}
10. Deadlock Detection and Prevention
Problem: Create a deadlock scenario (e.g., two threads acquiring locks in reverse order), then fix it.
Key Concept: Lock ordering, timeouts with tryLock().
Hint:

java
// Deadlock version:
Thread 1: lockA → lockB  
Thread 2: lockB → lockA  
// Fix: Always acquire lockA before lockB.