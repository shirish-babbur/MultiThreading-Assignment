package org.webonise.producerconsumerproblem;

import java.util.ArrayList;
import java.util.Iterator;

public class ProducerWithException implements Runnable {
    private final ArrayList<Integer> sharedItem;

    public ProducerWithException(ArrayList<Integer> sharedItem) {
        // sets values of fields.
        this.sharedItem = sharedItem;
    }

    @Override
    public void run() {
        for (int i = 0; i < AppMainWithException.NO_OF_PRODUCTS; i++) {
            try {
                // produces the Product.
                produce(i);
                synchronized (sharedItem) {
                    sharedItem.notifyAll();
                    display();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void produce(int productNumber) throws Exception {
        // Wait if size of sharedItem is full.
        synchronized (sharedItem) {
            while (sharedItem.size() == AppMainWithException.size) {
                // Used Synchronized block to achieve mutual exclusion.
                System.out.println("Thread " + Thread.currentThread().getName() + " is Waiting SharedItem is full(size:" + sharedItem.size() + ")");
                sharedItem.wait();
            }
            // Otherwise add product and notify all waiting consumers.
            sharedItem.add(productNumber);
            System.out.println("Produced:" + productNumber);
            Thread.sleep(200);
        }
    }

    private void display() {
        // This Code will produce concurrentmodification exception.
        // Since other thread while iterating can modify the sharedItem.
        Iterator<Integer> iterator = sharedItem.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next() + ",");
        System.out.println();
    }
}
