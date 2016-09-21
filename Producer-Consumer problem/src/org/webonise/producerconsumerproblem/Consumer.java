package org.webonise.producerconsumerproblem;

import java.util.ArrayList;
import java.util.Iterator;

public class Consumer implements Runnable {
    private final ArrayList<Integer> sharedItemList;
    private int count = 0;

    public Consumer(ArrayList<Integer> sharedItemList) {
        // sets values of fields.
        this.sharedItemList = sharedItemList;
    }

    @Override
    public void run() {
        while (count != AppMain.NO_OF_PRODUCTS) {
            try {
                // Consumer consumes Product.
                System.out.println("Consumed:" + consume());
                count++;
                synchronized (sharedItemList) {
                    sharedItemList.notifyAll();
                    display();
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int consume() throws Exception {
        synchronized (sharedItemList) {
            // Wait if size of sharedItemList is empty.
            while (sharedItemList.isEmpty()) {
                // Used Synchronized block to achieve mutual exclusion.
                System.out.println("Thread " + Thread.currentThread().getName() + " is Waiting SharedItem is Empty.(size:" + sharedItemList.size() + ")");
                sharedItemList.wait();
            }
            // Consumer consumes item and notifies other waiting Threads.
            return sharedItemList.remove(0);
        }
    }

    private void display() {
        // If don't synchronize while implementing iterator in this case there is a chance
        // that other while iterating other thread may modify the sharedItemList list.which
        // will cause to throw concurrentmodification exception.
        // we can produce concurrentmodification exception by removing synchronize block.
        synchronized (sharedItemList) {
            Iterator<Integer> iterator = sharedItemList.iterator();
            while (iterator.hasNext())
                System.out.print(iterator.next() + ",");
            System.out.println();
        }
    }
}
