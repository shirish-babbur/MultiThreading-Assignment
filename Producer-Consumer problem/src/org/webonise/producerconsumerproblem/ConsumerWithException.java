package org.webonise.producerconsumerproblem;

import java.util.ArrayList;
import java.util.Iterator;

public class ConsumerWithException implements Runnable {
    private final ArrayList<Integer> sharedItemList;
    private int count = 0;

    public ConsumerWithException(ArrayList<Integer> sharedItemList) {
        // sets values of fields.
        this.sharedItemList = sharedItemList;
    }

    @Override
    public void run() {
        while (count != AppMainWithException.NO_OF_PRODUCTS) {
            try {
                // Consumer consumes Product.
                System.out.println("Consumed:" + consume());
                count++;
                synchronized (sharedItemList) {
                    sharedItemList.notifyAll();
                    display();
                    Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int consume() throws Exception {
        // Wait if size of sharedItemList is empty.
        synchronized ((sharedItemList)) {
            // Used Synchronized block to achieve mutual exclusion.
            while (sharedItemList.isEmpty()) {
                System.out.println("Thread " + Thread.currentThread().getName() + " is Waiting SharedItem is Empty.(size:" + sharedItemList.size() + ")");
                sharedItemList.wait();
            }
            // Consumer consumes item and notifies other waiting Threads.
            return sharedItemList.remove(0);
        }
    }

    private void display() {
        // This code will produce concurrentmodification exception.
        // Since other thread while iterating can modify the sharedItemList.
        Iterator<Integer> iterator = sharedItemList.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next() + ",");
        System.out.println();
    }
}
