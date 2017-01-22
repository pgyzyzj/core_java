package com.nop.concurrent.barrier;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TODO:  这个例子是来源jdk的CyclicBarrier类，没有看明白他javadoc中的例子  2017-01-03
 * 1:barrier在初始化时的Runnable的run方法会在等待线程唤醒之前调用...
 * 2:如it方法返回0了，还有线程调用await(),那么barrier又重新进入计数状态，这就是为什么叫循环栏栅栏了（类的名字，这也是它与CountDownLatch的区别了）
 * 3: if one thread throws BrokenBarrierException or InterruptedException, the other waiting thread will receive this signal and enter the catch block or
 * rethrow the same exception.  (haha, i can write en comments)
 *
 * attention: the worker thread's done() method is used to retry the task, unless the barrier runnable task done all things right.
 * In the blow example ,when mergeRow succeed,the done() method will return true.
 * Created by yangzijun on 17-1-3.
 */
public class Solver {

    final int N;
    final float[][] data;
    final CyclicBarrier barrier;

    class Worker implements Runnable {
        int myRow;

        Worker(int row) {
            myRow = row;
        }

        public void run() {
            while (!done()) {
                processRow(myRow);

                try {
                    int index=barrier.await();
                    if(index==0){
                        System.out.println("the last task done.");
                    }
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private void processRow(int i) {
            try {
                System.out.println("worker thread is running...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private boolean done(){
            return true;
        }
    }

    public Solver(float[][] matrix) {
        data = matrix;
        N = matrix.length;
        barrier = new CyclicBarrier(N,
                new Runnable() {
                    public void run() {
                        System.out.println("all sub tasks has been done");
                        mergeRow();
                    }
                });
        for (int i = 0; i < N; ++i)
            new Thread(new Worker(i)).start();

        waitUntilDone();
    }

    public void mergeRow(){

    }

    public void waitUntilDone() {
        System.out.println("all done");
    }

    public static void main(String[] args){
        float[][] matrix={{1,2},{3,1}};
        Solver solver=new Solver(matrix);
    }

}
