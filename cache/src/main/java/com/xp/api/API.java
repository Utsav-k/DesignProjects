package com.xp.api;

public class API {
    public String getResponse(int id) {
        try {
            long startTime = System.currentTimeMillis();
            Thread.sleep(id*100); // Simulating network delay
            long timeTaken = System.currentTimeMillis() - startTime;
            return "API Response took " + timeTaken;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error: Interrupted while fetching response";
        }
    }
}
