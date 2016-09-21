package org.webonise.producerconsumerproblem;

import java.util.ArrayList;

public class AppMain {
    public static final int NO_OF_PRODUCTS = 7;
    public static final int size = 4;
    private static ArrayList<Integer> sharedItemList = new ArrayList<Integer>();

    public static void main(String[] args) {
        // Producer Thread created.
        Thread producerThread = new Thread(new Producer(sharedItemList), "ProducerThread");
        // Consumer Thread created.
        Thread consumerThread = new Thread(new Consumer(sharedItemList), "ConsumerThread");
        // Both Threads Started.
        consumerThread.start();
        producerThread.start();
        try {
            consumerThread.join();
            producerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Joined both the threads Producer and Consumer to Main Thread.\nExiting");
    }
}
