package org.webonise.producerconsumerproblem;

import java.util.ArrayList;

public class AppMainWithException {
    public static final int NO_OF_PRODUCTS = 7;
    private static ArrayList<Integer> sharedItem = new ArrayList<Integer>();
    public static final int size = 4;

    public static void main(String[] args) {
        // Producer Thread created.
        Thread producerThread = new Thread(new ProducerWithException(sharedItem), "ProducerThread");
        // Consumer Thread created.
        Thread consumerThread = new Thread(new ConsumerWithException(sharedItem), "ConsumerThread");
        // Both Threads Started.
        producerThread.start();
        consumerThread.start();
        try {
            consumerThread.join();
            producerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Joined both the threads Producer and Consumer to Main Thread.\nExiting");
    }
}
