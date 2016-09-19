package org.webonise.producerconsumerproblem;

import java.util.ArrayList;
import java.util.Iterator;

public class ConsumerWithException implements Runnable{
    private final ArrayList<Integer> sharedItem;
    private int size;
    public ConsumerWithException(ArrayList<Integer>sharedItem,int size){
        // sets values of fields.
        this.sharedItem=sharedItem;
        this.size=size;
    }
    @Override
    public void run() {
        while(true){
            try {
                // Consumer consumes Product.
                System.out.println("Consumed:"+consume());
                display();
                Thread.sleep(10);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private int consume()throws Exception{
        // Wait if size of sharedItem is empty.
        while(sharedItem.isEmpty()){
            // Used Synchronized block to achieve mutual exclusion.
            synchronized (sharedItem){
                System.out.println("Thread "+Thread.currentThread().getName()+" is Waiting SharedItem is Empty.(size:"+sharedItem.size()+")");
                sharedItem.wait();
            }
        }
        // Consumer consumes item and notifies other waiting Threads.
        // Used Synchronized block to achieve mutual exclusion.
        synchronized (sharedItem){
            sharedItem.notifyAll();
            return sharedItem.remove(0);
        }
    }
    private void display(){
        // This code will produce concurrentmodification exception.
        // Since other thread while iterating can modify the sharedItem.
        Iterator<Integer> iterator=sharedItem.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next()+",");
        System.out.println();
    }
}
