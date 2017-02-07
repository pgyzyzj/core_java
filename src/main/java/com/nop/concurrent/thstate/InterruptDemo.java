package com.nop.concurrent.thstate;


/**
 * 当Thread.sleep方法调用的时候，会将当前线程从待执行线程队列中移除。
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
