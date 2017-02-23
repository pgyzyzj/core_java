package com.nop.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangzijun on 17-2-16.
 * copied from  <code>java.util.concurrent.Lock.Condition's</code> javadoc
 * 利用Lock对象及condition实现的一个有界队列...
 *
 * main方法的验证是我自己写的，最终消费完成后，程序会阻塞，因此没有生产者线程已经执行害怕了，buffer为空，就一直阻塞了
 */
public class BoundedBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final BoundedBuffer buffer=new BoundedBuffer();
        for(int i=0;i<50;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        buffer.put("obj");
                        Thread.sleep(200);
                        System.out.println(Thread.currentThread().getName()+"生产了一个对象");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Long duration=System.currentTimeMillis()+15000;
               while(System.currentTimeMillis()<duration) {
                   try {
                       buffer.take();
                       System.out.println("消费了一个对象");
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        }).start();
    }
}
