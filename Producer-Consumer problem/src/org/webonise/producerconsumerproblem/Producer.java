package org.webonise.producerconsumerproblem;

import java.util.ArrayList;
import java.util.Iterator;

public class Producer implements Runnable{
    private final ArrayList<Integer> sharedItem;
    private int size;
    public Producer(ArrayList<Integer>sharedItem,int size){
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
        }
    }
    private void display(){
        // If don't synchronize while implementing iterator in this case there is a chance
        // that other while iterating other thread may modify the sharedItem list.which
        // will cause to throw concurrentmodification exception.
        // we can produce concurrentmodification exception by removing synchronize block.
        synchronized (sharedItem){
        Iterator<Integer> iterator=sharedItem.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next()+",");
        System.out.println();
        }
    }
}
