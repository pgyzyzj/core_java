package com.nop.vm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangzijun on 17-3-28.
 */
public class VmMaxThreadCount {
    private static final AtomicInteger threadCount=new AtomicInteger(0);
    public static void main(String[] args) {
        try {
            while (threadCount.get()<5000) {
                //while (true) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = threadCount.incrementAndGet();
                        while (true) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                t.start();
            }
        }catch(Error e){
            System.out.println("当前线程数:"+threadCount.get());
            e.printStackTrace();
        }

    }
}
