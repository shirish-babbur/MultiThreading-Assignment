package org.webonise.producerconsumerproblem;

import java.util.ArrayList;

public class AppMainWithException {
    public static void main(String[] args) {
        ArrayList<Integer> sharedItem=new ArrayList<Integer>();
        int size=4;
        // Producer Thread created.
        Thread producerThread=new Thread(new ProducerWithException(sharedItem,size),"ProducerThread");
        // Consumer Thread created.
        Thread consumerThread=new Thread(new ConsumerWithException(sharedItem,size),"ConsumerThread");
        // Both Threads Started.
        producerThread.start();
        consumerThread.start();
    }
}
