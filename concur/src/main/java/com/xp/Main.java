package com.xp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        SimpleMessageQueue q = new SimpleMessageQueue();

        // Consume
        Executors.newSingleThreadExecutor().submit(() -> {
            while(true) {
                try {
                    q.consume();
                    // Thread.sleep(600);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // produce
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            while(true) {
                try {
                    Thread.sleep(800);
                    q.produce("message");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}