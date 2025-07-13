package com.xp.sequential;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SequentialThreadExecution {


    // Execute the threads in order
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> System.out.println("T1"));
        Thread t2 = new Thread(() -> System.out.println("T2"));
        Thread t3 = new Thread(() -> System.out.println("T3"));
        
        // t1.start();
        // t1.join();
        // t2.start();
        // t2.join();
        // t3.start();

        // Can use executor service as well

        // Executor Service

        /*
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.submit(t1); 
        executor.submit(t2);
        executor.submit(t3);

        executor.shutdown();
        
        */

        // or Use CompletableFuture
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> System.out.println("T1"))
            .thenRun(() -> System.out.println("T2"))
            .thenRun(() -> System.out.println("T3"));

        cf.join();  // Wait for entire chain to complete
    }
}

