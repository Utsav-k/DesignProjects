package com.xp;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Note about synchronized methods - 
 * 1. synchronized methods lock the object they belong to, not the objects they interact with.
 * 2. To protect shared data (like BankAccount), synchronization must happen where the data lives 
 *      (or lock explicitly on that data).
 * 
 * In the SimpleMessageQueue example, the lock occurs on the SimpleMessageQueue Object (including queue attribute)
 */
public class SimpleMessageQueue {

    private final Queue<String> queue = new LinkedList<>();
    private final int MAX_SIZE = 5;

    public synchronized void produce(String message) throws InterruptedException {
        while(queue.size() == MAX_SIZE) {
            System.out.println("Max Size is reached, waiting for the consumer to consume");
            wait(); // This thread sleeps and the resource "queue" is opened for other threads.
        }
        queue.add(message);
        notifyAll(); 
    }

    public synchronized void consume() throws InterruptedException {
        while(queue.isEmpty()) {
            System.out.println("Consumer is Waiting...");
            wait();
        }
        System.out.println("message detected");
        String msg = queue.poll();
        System.out.println(msg);
        notifyAll();
    }
}
 
// Improved Variant with a block instead

/*
public class SimpleMessageQueue {
    private final Queue<String> queue = new LinkedList<>();
    private final int MAX_SIZE;
    private final Object lock = new Object(); // Explicit lock object

    public SimpleMessageQueue(int maxSize) { 
        this.MAX_SIZE = maxSize; 
    }

    public void produce(String message) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == MAX_SIZE) {
                lock.wait();
            }
            queue.add(message);
            lock.notifyAll(); // Wakes all waiters (safer for multiple producers/consumers)
        }
    }

    public String consume() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();
            }
            String msg = queue.poll();
            lock.notifyAll();
            return msg;
        }
    }
}
*/
