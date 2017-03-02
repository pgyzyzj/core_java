package com.nop.concurrent.lock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by yangzijun on 17-3-2.
 */
public class DeadLockDetect {
    public static void main(String[] args) throws InterruptedException {
        DeadLock deadLock = new DeadLock();
        Thread thread1 = new Thread(deadLock, "t1");
        Thread thread2 = new Thread(deadLock, "t2");
        thread1.start();
        thread2.start();

        while (true) {
            ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
            long[] ids = mbean.findDeadlockedThreads();
            System.out.println("Dead lock detected.");
            if (ids != null) {
                ThreadInfo[] infos = mbean.getThreadInfo(ids);
                for(int i=0;i<infos.length;i++){
                    GsonBuilder gb=new GsonBuilder();
                    gb.setPrettyPrinting();
                    Gson gson=gb.create();
                    System.out.println(gson.toJson(infos[i])+"\n");
                }
                break;
            }
            Thread.sleep(500);
        }

    }
}
