package com.nop.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 1:这个例子目前还没有得到期望的结果，原因是不能保证会出现两个线程同时运行
 created by yangzijun on 17-2-6.
 */
public class VolatileNotSafe {

    private static class NumberRange {
        private int lower;
        private int upper;

        public NumberRange() {
            lower = 0;
            upper = 5;
        }

        public int getUpper() {
            return upper;
        }

        public void setUpper(int upper) {
            if (upper < lower) {
                throw new IllegalArgumentException();
            }
            this.upper = upper;
        }

        public int getLower() {
            return lower;
        }

        public void setLower(int lower) {
            if (lower > upper) {
                throw new IllegalArgumentException();
            }
            this.lower = lower;
        }

        public String getValue() {
            return String.format("(%s,%s)", lower, upper);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        final NumberRange numberRange = new NumberRange();
        System.out.println("origin value:" + numberRange.getValue());
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                numberRange.setUpper(3);
//                System.out.println("thread1 changed upper to 3");
//                System.out.println("thread1 done");
            }
        });
        t1.setName("thread1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("value in thread2: "+volatileNotSafe.getValue());
                numberRange.setLower(4);
//                System.out.println("thread2 done");
            }
        });
        t2.setName("thread2");
        t1.start();
        t2.start();
        Thread.sleep(100);
        System.out.println("final value:" + numberRange.getValue());
    }
}
