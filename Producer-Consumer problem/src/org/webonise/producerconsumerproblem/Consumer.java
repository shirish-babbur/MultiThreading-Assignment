package org.webonise.producerconsumerproblem;

import java.util.ArrayList;
import java.util.Iterator;

public class Consumer implements Runnable{
    private final ArrayList<Integer> sharedItem;
    private int size;
    public Consumer(ArrayList<Integer>sharedItem,int size){
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
                Thread.sleep(50);
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
