package com.nop.concurrent.barrier;

import java.util.concurrent.Semaphore;

/**
 * Created by yangzijun on 17-2-22.
 * this program will hang the process ..
 */
public class SemaphoreHang implements Runnable{

    private Semaphore resource1;
    private Semaphore resource2;

    public SemaphoreHang(Semaphore s1,Semaphore s2){
        this.resource1=s1;
        this.resource2=s2;
    }

    @Override
    public void run() {
        try {
            resource1.acquire();
            System.out.println("locked resource1");
            Thread.sleep(200);
            resource2.acquire();
            System.out.println("locked resource2");
            resource2.release();
            System.out.println("release resource2");
            resource1.release();
            System.out.println("release resource1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Semaphore s1=new Semaphore(1);
        Semaphore s2=new Semaphore(1);
        Runnable run=new SemaphoreHang(s1,s2);
        Runnable run2=new SemaphoreHang(s2,s2);
        Thread t1=new Thread(run);
        Thread t2=new Thread(run2);
        t1.start();
        t2.start();
    }
}
