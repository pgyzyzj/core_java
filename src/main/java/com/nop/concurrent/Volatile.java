package com.nop.concurrent;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile关键字，用于保证线程读取到的共享变量一定是最新的，但是不能保证原子性操作，因此线程安全问题不能只是简单的添加volatile关键字修饰.
 * 这个例子是从网上摘录的，写得有点乱，逻辑不够清楚。。
 * 参见这篇tp://www.ibm.com/developerworks/cn/java/j-jtp06197.html
 * 文章指出：Volatile 变量具有 synchronized 的可见性特性，但是不具备原子特性。
 * 这就是说线程能够自动发现 volatile 变量的最新值。Volatile 变量可用于提供线程安全，但是只能应用于非常有限的一组用例：
 * 多个变量之间或者某个变量的当前值与修改后值之间没有约束。
 * 因此，单独使用 volatile 还不足以实现计数器、互斥锁或任何具有与多个变量相关的不变式（Invariants）的类（例如 “start <=end”）。
 *
reated by yangzijun on 17-2-6.
 */
public class Volatile extends Object implements Runnable {
    //value变量没有被标记为volatile
    private int value;
    //missedIt变量被标记为volatile
    private volatile boolean missedIt;
    //creationTime不需要声明为volatile，因为代码执行中它没有发生变化
    private long creationTime;


    public Volatile() {
        value = 10;
        missedIt = false;
        //获取当前时间，亦即调用Volatile构造函数时的时间
        creationTime = System.currentTimeMillis();
    }


    public void run() {
        print("entering run()");

        //循环检查value的值是否不同
        while (value < 20) {
            //如果missedIt的值被修改为true，则通过break退出循环
            if (missedIt) {
            //进入同步代码块前，将value的值赋给currValue
                int currValue = value;
                //在一个任意对象上执行同步语句，目的是为了让该线程在进入和离开同步代码块时，
                //将该线程中的所有变量的私有拷贝与共享内存中的原始值进行比较，
                //从而发现没有用volatile标记的变量所发生的变化
                Object lock = new Object();
                synchronized (lock) {
                    //不做任何事
                }
                //离开同步代码块后，将此时value的值赋给valueAfterSync
                int valueAfterSync = value;
                print("in run() - see value=" + currValue + ", but rumor has it that it changed!");
                print("in run() - valueAfterSync=" + valueAfterSync);
                break;
            }
        }
        print("leaving run()");
    }


    public void workMethod() throws InterruptedException {
        print("entering workMethod()");
        print("in workMethod() - about to sleep for 2 seconds");
        Thread.sleep(2000);
        //仅在此改变value的值
        missedIt = true;
        // value = 50;
        print("in workMethod() - just set value=" + value);
        print("in workMethod() - about to sleep for 5 seconds");
        Thread.sleep(5000);
        //仅在此改变missedIt的值
        // missedIt = true;
        value = 50;
        print("in workMethod() - just set missedIt=" + missedIt);
        print("in workMethod() - about to sleep for 3 seconds");
        Thread.sleep(3000);
        print("leaving workMethod()");
    }


    /*
    *该方法的功能是在要打印的msg信息前打印出程序执行到此所化去的时间，以及打印msg的代码所在的线程
    */
    private void print(String msg) {
        //使用java.text包的功能，可以简化这个方法，但是这里没有利用这一点
        long interval = System.currentTimeMillis() - creationTime;
        String tmpStr = " " + (interval / 1000.0) + "000";
        System.out.println(interval+": " + msg);
    }


    public static void main(String[] args) {
        try {
            //通过该构造函数可以获取实时时钟的当前时间
            Volatile vol = new Volatile();

            //稍停100ms，以让实时时钟稍稍超前获取时间，使print（）中创建的消息打印的时间值大于0
            Thread.sleep(100);

            Thread t = new Thread(vol);
            t.start();

            //休眠100ms，让刚刚启动的线程有时间运行
            Thread.sleep(100);
            //workMethod方法在main线程中运行
            vol.workMethod();
        } catch (InterruptedException x) {
            System.err.println("one of the sleeps was interrupted");
        }
    }
}
