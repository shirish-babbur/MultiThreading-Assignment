package org.webonise.producerconsumerproblem;

import java.util.ArrayList;
import java.util.Iterator;

public class ProducerWithException implements Runnable{
    private final ArrayList<Integer> sharedItem;
    private int size;
    public ProducerWithException(ArrayList<Integer>sharedItem,int size){
        // sets values of fields.
        this.sharedItem=sharedItem;
        this.size=size;
    }
    @Override
    public void run() {
        for(int i=0;i<7;i++){
            System.out.println("Produced:"+i);
            try {
                // produces the Product.
                produce(i);
                display();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private void produce(int productNumber) throws Exception{
        // Wait if size of sharedItem is full.
        while(sharedItem.size()==size){
            // Used Synchronized block to achieve mutual exclusion.
            synchronized (sharedItem){
                System.out.println("Thread "+Thread.currentThread().getName()+" is Waiting SharedItem is full(size:"+sharedItem.size()+")");
                sharedItem.wait();
            }
        }
        // Otherwise add product and notify all waiting consumers.
        // Used Synchronized block to achieve mutual exclusion.
        synchronized(sharedItem){
            sharedItem.add(productNumber);
            sharedItem.notifyAll();
            Thread.sleep(200);
        }
    }
    private void display(){
        // This Code will produce concurrentmodification exception.
        // Since other thread while iterating can modify the sharedItem.
        Iterator<Integer> iterator=sharedItem.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next()+",");
        System.out.println();
    }
}
