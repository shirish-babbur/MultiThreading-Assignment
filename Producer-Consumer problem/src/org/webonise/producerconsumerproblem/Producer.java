package org.webonise.producerconsumerproblem;

import java.util.ArrayList;
import java.util.Iterator;

public class Producer implements Runnable {
    private final ArrayList<Integer> sharedItemList;

    public Producer(ArrayList<Integer> sharedItemList) {
        // sets values of fields.
        this.sharedItemList = sharedItemList;
    }

    @Override
    public void run() {
        for (int i = 0; i < AppMain.NO_OF_PRODUCTS; i++) {
            try {
                // produces the Product.
                produce(i);
                synchronized (sharedItemList) {
                    sharedItemList.notifyAll();
                    display();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void produce(int productNumber) throws Exception {
        synchronized (sharedItemList) {
            // Used Synchronized block to achieve mutual exclusion.
            // Wait if size of sharedItemList is full.
            while (sharedItemList.size() == AppMain.size) {
                System.out.println("Thread " + Thread.currentThread().getName() + " is Waiting SharedItem is full(size:" + sharedItemList.size() + ")");
                sharedItemList.wait();
            }
            // Otherwise add product and notify all waiting consumers.
            sharedItemList.add(productNumber);
            System.out.println("Produced:" + productNumber);
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
