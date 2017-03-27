package com.nop.zk.emulator;

import org.apache.zookeeper.KeeperException;

import java.util.Random;

/**
 * Created by yangzijun on 17-3-22.
 * TODO:
 * after all threads  entered the barrier,if the job will be done in almost 0 ms ,after then calling leave will cause
 * other job thread re-enter waiting...
 */
public class SomeKindOfConCurrentJob {

    private boolean allDone = false;

    private static class JobEmulator implements Runnable {
        private ZkBarrier barrier;

        JobEmulator(ZkBarrier zk) {
            this.barrier = zk;
        }

        @Override
        public void run() {
            try {
                Long begin = System.currentTimeMillis();
                boolean canStart = barrier.enterBarrier();
                Long end = System.currentTimeMillis();
                if (canStart) {
                    System.out.println(Thread.currentThread().getName() + " enter barrier:" + (end - begin) + "ms elapsed");
                    //after commented bellow sentence,this program will not running as expected.
                    Thread.sleep(new Random().nextInt(1000));
                }
                begin = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + "+ wanting leaving now");
                boolean finished = barrier.leave();
                end = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + " leaving barrier cost " + (end - begin) + "ms,leave barrier:" + finished);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ZkBarrier barrier = new ZkBarrier("127.0.0.1:2181", "/yzj", 5);
        for (int i = 0; i < 5; i++) {
            JobEmulator jobEmulator = new JobEmulator(barrier);
            Thread thread = new Thread(jobEmulator);
            thread.setName("job-thread-" + Integer.valueOf(i));
            thread.start();
        }
        while (true) {
            synchronized (barrier) {
                if (!barrier.isAllDone()) {
                    System.out.println(Thread.currentThread().getName() + " main thread waiting...");
                    barrier.wait();
                }
                System.out.println(Thread.currentThread().getName() + " all done.main thread finished");
                break;
            }
        }
    }
}
