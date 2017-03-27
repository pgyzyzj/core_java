package com.nop.vm;

/**
 * Created by yangzijun on 17-3-27.
 */
public class GetHighestCpuThread {
    public static void main(String[] args) {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                }
            }
        });
        t.start();
    }
}
