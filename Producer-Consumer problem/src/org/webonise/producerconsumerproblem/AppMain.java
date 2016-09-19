package org.webonise.producerconsumerproblem;

import java.util.ArrayList;

public class AppMain {
    public static void main(String[] args) {
        ArrayList<Integer> sharedItem=new ArrayList<Integer>();
        int size=4;
        // Producer Thread created.
        Thread producerThread=new Thread(new Producer(sharedItem,size),"ProducerThread");
        // Consumer Thread created.
        Thread consumerThread=new Thread(new Consumer(sharedItem,size),"ConsumerThread");
        // Both Threads Started.
        producerThread.start();
        consumerThread.start();
    }
}
