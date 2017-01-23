package com.nop.concurrent.lock;

import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * Created by yangzijun on 17-1-23.
 *  t acquire s1,t2 acquire s2,when t want to acquire s2 then blocked, t2 want to acquire s1 also blocked..
 *  t and t2 both were waiting for each each to release the semaphore...Dead Lock happened...
 */


public class SemaphoreDeadLock implements Runnable {
    private Semaphore first;
    private Semaphore second;

    public SemaphoreDeadLock(Semaphore s1, Semaphore s2) {
        first = s1;
        second = s2;
    }

    public void run() {
        try {
            Thread t = Thread.currentThread();
            first.acquire();
            System.out.println(t + " acquired " + first);

            Thread.sleep(200); // demonstrate deadlock
            second.acquire();
            System.out.println(t + " acquired " + second);

            second.release();
            System.out.println(t + " released " + second);
            first.release();
            System.out.println(t + " released " + first);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);
        Thread t = new Thread(new SemaphoreDeadLock(s1, s2));
        // now reverse them ... here comes trouble!
        Thread t2 = new Thread(new SemaphoreDeadLock(s2, s1));
        t.start();
        t2.start();
        t.join();
        t2.join();
        System.out.println("We got lucky!");
    }

}
