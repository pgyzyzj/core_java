package com.nop.concurrent.lock;

import java.util.Random;

/**
 * Created by yangzijun on 17-2-16.
 *
 * 哈哈，这个例子是从javacodegeek上来的，原来的例子有问题，在new Thread时使用了new DeadLock，如果是这样，那么两个线程都没有临界资源，永远不会出现抢资源非发生死锁
 */
public class DeadLock implements Runnable {

    final Object resource1 = new Object();
    final Object resource2 = new Object();
    final Random random = new Random(System.currentTimeMillis());


    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            boolean b = random.nextBoolean();
            if (b) {
                System.out.println(Thread.currentThread().getName() + " trying to lock resource1");
                synchronized (resource1) {
                    System.out.println(Thread.currentThread().getName() + " locked resource1");
                    System.out.println(Thread.currentThread().getName() + " trying to lock resource2");
                    synchronized (resource2) {
                        System.out.println(Thread.currentThread().getName() + " locked resource2");
                    }
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " trying to lock resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " locked resource2");
                    System.out.println(Thread.currentThread().getName() + " trying to lock resource1");
                    synchronized (resource1) {
                        System.out.println(Thread.currentThread().getName() + " locked resource1");
                    }
                }

            }
        }
    }

    public static void main(String[] args) {
        DeadLock deadLock=new DeadLock();
        Thread thread1 = new Thread(deadLock, "t1");
        Thread thread2 = new Thread(deadLock, "t2");
        thread1.start();
        thread2.start();
    }
}
