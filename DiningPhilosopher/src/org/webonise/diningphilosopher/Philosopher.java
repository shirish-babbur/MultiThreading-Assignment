package org.webonise.diningphilosopher;

import java.util.concurrent.locks.Lock;

class Philosopher implements Runnable {
    // The philosopher's unique id
    private int id;
    // The chopsticks this philosopher may use
    private Lock leftChopstick;
    private Lock rightChopstick;
    public Philosopher (int id, Lock leftChopstick, Lock rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }
    // Repeatedly think, pick up chopsticks, eat and put down chopsticks
    public void run() {
        try {
            while (true) {
                think();
                pickUpLeftChopstick();
                pickUpRightChopstick();
                eat();
                putDownChopsticks();
            }
        } catch (Exception e) {
            System.out.println("Philosopher " + id + " was interrupted.\n");
        }
    }
    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.\n");
        System.out.flush();
        Thread.sleep (3000);
    }
    // Locks the left chopstick to signify that this philosopher is holding it
    private void pickUpLeftChopstick() throws InterruptedException {
        leftChopstick.lock();
        System.out.println("Philosopher " + id + " is holding left chopstick.\n");
        System.out.flush();
    }
    // Locks the right chopstick to signify that this philosopher is holding it
    private void pickUpRightChopstick() throws InterruptedException {
        rightChopstick.lock();
        System.out.println("Philosopher " + id + " is holding right chopstick.\n");
        System.out.flush();
    }
    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.\n");
        System.out.flush();
        Thread.sleep (3000);
    }
    // Releases the locks on both chopsticks to model putting them down so the
    // other philosophers can use them.
    private void putDownChopsticks() {
        leftChopstick.unlock();
        System.out.println("Philosopher " + id + " is released left chopstick.\n");
        rightChopstick.unlock();
        System.out.println("Philosopher " + id + " is released right chopstick.\n");
    }
}