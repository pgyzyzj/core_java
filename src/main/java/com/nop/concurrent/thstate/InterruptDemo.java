package com.nop.concurrent.thstate;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by yangzijun on 17-1-13.
 */
public class InterruptDemo extends Thread{

    public InterruptDemo(String name){
        super(name);
    }

    public void run() {
        try{
            Thread.sleep(Long.MAX_VALUE);
        }catch (InterruptedException e){
            //important here,catch the InterruptedException will clear the interrupted state,why? cause interrupted is one of thread's state
            //A thread can be re-run after interrupted.
            System.out.println(Thread.currentThread().getName()+" was interrupted execution.");
        }
        while(!Thread.interrupted()){
            //do nothing
        }
        System.out.println(Thread.currentThread().getName()+" was interrupted again.");
    }

    public static void main(String[] args) throws InterruptedException {
        InterruptDemo interruptDemo=new InterruptDemo("interruptedDemoThread");
        interruptDemo.start();
        System.out.println("main Thread sleep 5s ,after then interrupt the demo thread");
        Thread.sleep(5000);
        interruptDemo.interrupt();
        System.out.println("main Thread sleep the second 5s ,after then interrupt the demo thread");
        Thread.sleep(5000);
        interruptDemo.interrupt();
    }




}
