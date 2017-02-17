package com.nop.concurrent.barrier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangzijun on 17-2-17.
 * cost输出：１，２，３，４，５
 */
public class SemaphoreExample implements Runnable {

    private final AtomicInteger counter = new AtomicInteger(0);

    private Semaphore semaphore;
    public SemaphoreExample(Semaphore semaphore){
        this.semaphore=semaphore;
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        Semaphore semaphore = new Semaphore(1, true);
        for (int i = 0; i < 5; i++) {
            Thread t=new Thread(new SemaphoreExample(semaphore));
            t.start();
            //threadPool.execute(new SemaphoreExample(semaphore));
        }
        threadPool.shutdown();
    }

    @Override
    public void run() {
        try {
            long begin=System.currentTimeMillis();
            semaphore.acquire();
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "acquire the lock and run...counter=" + counter.incrementAndGet());
            if (counter.get() > 3) {
                throw new IllegalStateException("too many threads are running..");
            }
            semaphore.release();
            counter.decrementAndGet();
            System.out.println("cost:"+(System.currentTimeMillis()-begin)/1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
