package org.example;

import java.util.Random;

public class ModifyDeadlock implements Runnable {
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();
    private final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Thread myThread1 = new Thread(new ModifyDeadlock(), "thread-1");
        Thread myThread2 = new Thread(new ModifyDeadlock(), "thread-2");
        myThread1.start();
        myThread2.start();
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            boolean b = random.nextBoolean();
            if (b) {
                lockResourcesInOrder(resource1, resource2);
            } else {
                lockResourcesInOrder(resource1, resource2); // Even when "random", **still same order**
            }
        }
    }

    private void lockResourcesInOrder(Object first, Object second) {
        System.out.println("[" + Thread.currentThread().getName() + "] Trying to lock resource 1.");
        synchronized (first) {
            System.out.println("[" + Thread.currentThread().getName() + "] Locked resource 1.");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[" + Thread.currentThread().getName() + "] Trying to lock resource 2.");
            synchronized (second) {
                System.out.println("[" + Thread.currentThread().getName() + "] Locked resource 2.");
                // Critical section
            }
        }
    }
}
