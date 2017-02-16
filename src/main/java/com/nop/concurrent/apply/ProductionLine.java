package com.nop.concurrent.apply;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Created by yangzijun on 17-2-16.
 * 这个最简单的还是使用ArrayBlockQueue来实现，因为ArrayBlockQueue内部采用了ReentrantLock及Condition来实现，其实就是BoundedBuffer的实现
 * 真的是刻骨铭心，写了一个小时的生产者消息者，程序一度不能正常运行。。。。。
 * 刚抽烟又想了一翻，锁一定是要加在临界资源上。。
 * 不然生产者线程与消费者线程就会永远只有一个线程能拿到锁，因此程序就出现生产一个，消费一个的情况
 * 另外：线程wait()之后，是从wait()处开始重新运行，因此在wait()代码块之前检测的临界资源务必要
 重新检测一翻，典型的做法就是使用while语句块来代码if
 */
public class ProductionLine {
    static class Pool {
        final List<Object> pool = new ArrayList<Object>(10);

        public void produce() throws InterruptedException {
            synchronized (pool) {
                while (pool.size() == 10) {
                    pool.wait();
                }
                pool.add(0);
                Thread.sleep(30);
                pool.notify();
                System.out.println(Thread.currentThread().getName() + ",生产了一个产品，产品池数量:" + pool.size());
            }
        }

        public Object consume() throws InterruptedException {
            Object obj = null;
            synchronized (pool) {
                // important here,if you use if statement to check the size,when you invoke pool.remote(0),you may get IndexOutOfBoundsExceptions
                while (pool.size() == 0) {
                    pool.wait();
                }
                obj = pool.remove(0);
                Thread.sleep(90);
                pool.notify();
                System.out.println(Thread.currentThread().getName() + ",消费了一个产品，产品池数量:" + pool.size());
            }
            return obj;
        }

        public int getSize() {
            return pool.size();
        }
    }


    static class Producer implements Runnable {
        private Pool pool;

        public Producer(Pool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            try {
                pool.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class Consumer implements Runnable {

        private Pool pool;

        public Consumer(Pool pool) {
            this.pool = pool;
        }

        public void consumer() {
            try {
                pool.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            consumer();
        }
    }

    public static void main(String[] args) {
        Pool pool = new Pool();
        for (int i = 0; i < 50; i++) {
            Thread consumer = new Thread(new Consumer(pool), "consumer"+i);
            consumer.start();
            Thread producer = new Thread(new Producer(pool), "producer"+i);
            producer.start();
        }

    }
}
