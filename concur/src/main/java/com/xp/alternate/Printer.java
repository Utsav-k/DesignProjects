package com.xp.alternate;

/**
 * Thread 1 and Thread 2 prints the number alternatively, Using wait(), notify() for the inter thread communication.
 */
public class Printer {
    private static final Object lock = new Object();
    private boolean turn = true;
    int num = 1;

    public void print(int N) {
        Thread t1 = new Thread(() -> {
            while(num <= N) {
                synchronized(lock) {
                    while(!turn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(num <= N) {
                        System.out.println("Thread 1 printed - " + num);
                        num++;
                        turn ^= true;
                        lock.notifyAll();
                    }
                }
            }
        }, "Thread 1");

        Thread t2 = new Thread(() -> {
            while(num <= N) {
                synchronized(lock) {
                    while(turn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(num <= N) {
                        System.out.println("Thread 2 printed - " + num);
                        num++;
                        turn ^= true;
                        lock.notifyAll();
                    }
                }
            }
        }, "Thread 2");

        t1.start();
        t2.start();

        // This prints beyond the number because of no inner check on the print. Need another check inside
    }
}
