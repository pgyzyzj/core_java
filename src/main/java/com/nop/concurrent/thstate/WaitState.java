package com.nop.concurrent.thstate;

/**
 * 通过debug就能观察到线程“thread－0“的当前状态是wait
 * Created by yangzijun on 17-2-6.
 */
public class WaitState {
    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this){
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(1000);

        System.out.println(1);
    }
}
