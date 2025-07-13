package com.xp;
import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyExample {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int MAX_SIZE = 2;
    private final Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {
                while (queue.size() == MAX_SIZE) {
                    System.out.println("Queue full, producer waiting...");
                    lock.wait(); // Releases lock, waits for notify()
                }
                queue.add(value++);
                System.out.println("Produced: " + value);
                lock.notify(); // Wakes up consumer
            }
            Thread.sleep(1000);
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (queue.isEmpty()) {
                    System.out.println("Queue empty, consumer waiting...");
                    lock.wait(); // Releases lock, waits for notify()
                }
                int value = queue.poll();
                System.out.println("Consumed: " + value);
                lock.notify(); // Wakes up producer
            }
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) {
        WaitNotifyExample example = new WaitNotifyExample();
        Thread producer = new Thread(() -> {
            try { example.produce(); } catch (InterruptedException e) {}
        });
        Thread consumer = new Thread(() -> {
            try { example.consume(); } catch (InterruptedException e) {}
        });
        producer.start();
        consumer.start();
    }
}