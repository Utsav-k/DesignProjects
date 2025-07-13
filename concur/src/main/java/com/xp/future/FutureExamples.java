package com.xp.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureExamples {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> {
            System.out.println("Running in : " + Thread.currentThread().getName());
        });
        f.join();

        // Future with return
        String s = "Input";
        CompletableFuture<String> stringOutput = CompletableFuture.supplyAsync(() -> {
            return "Future Output " + s;  
        });

        // future.get() is blocking
        System.out.println(stringOutput.get());

        // For non blocking result
        try {
            nonblockingFutureOutput("Input").thenAccept(result -> {
                System.out.println(result);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Blocking Return
        CompletableFuture<String> res = nonblockingFutureOutput( "NewString");
        System.out.println(res.get());
    }

    // It's advised to Return the Future itself from the function. Caller can handle if it's blocking or non-blocking
    private static CompletableFuture<String> nonblockingFutureOutput(String inputString) {
        return CompletableFuture.supplyAsync(() -> "Processed: " + inputString)
            .thenApply(String::toUpperCase) // Sync Transformation
            .exceptionally(ex -> "Fallback: " + ex.getMessage());
    }


    private static void examples() throws InterruptedException, ExecutionException {
        // Chain Async Operations, thenCompose()
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

        System.out.println(future.get()); // "Hello World"

        // Combine two futures
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> " World");

        CompletableFuture<String> combined = future1.thenCombine(future2, (s1, s2) -> s1 + s2);
        System.out.println(combined.get()); // "Hello World"


        // Thread Pool Control
        
        ExecutorService customPool = Executors.newFixedThreadPool(10);

        CompletableFuture.supplyAsync(() -> {
            return "Running in custom pool: " + Thread.currentThread().getName();
        }, customPool).thenAccept(System.out::println);

        customPool.shutdown(); // Always clean up!
    }


}
