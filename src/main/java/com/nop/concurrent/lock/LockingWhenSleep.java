package com.nop.concurrent.lock;

import com.alibaba.fastjson.JSON;
import com.google.gson.GsonBuilder;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangzijun on 17-3-3.
 * today found thread's sleep method will hold the lock when sleeping.. so let's prove it.
 * Yes,when sleep it will continuous hold the lock that already obtained.
 * 使用固有锁跟手工加锁效果是一致的，如：去掉synchronized，使用lock
 */
public class LockingWhenSleep {
    private static final Lock lock = new ReentrantLock();

    static class Worker{
        public static synchronized void finishJob() throws InterruptedException {
            System.out.println(Thread.currentThread().getName()+" got the lock and running");
            Long threadId=Thread.currentThread().getId();
            ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
            ThreadInfo threadInfo=mbean.getThreadInfo(threadId);
            System.out.println(new GsonBuilder().create().toJson(threadInfo));
            Thread.sleep(3000);
        }
    }
    public static void main(String[] args) {
        Thread worker1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Worker.finishJob();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
        worker1.setName("worker1");
        final Thread worker2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Long time = System.currentTimeMillis() + 5000;
                while (System.currentTimeMillis() < time) {
                    try {
                        Worker.finishJob();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
            }
        });
        worker2.setName("worker2");
        worker1.start();
        worker2.start();


    }
}
