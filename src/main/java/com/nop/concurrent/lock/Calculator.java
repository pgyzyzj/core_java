package com.nop.concurrent.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by yangzijun on 17-2-22.
 *
 * 读锁可以被多个线程同时获取，写锁在有读锁未释放时阻塞
 * 读锁在有写锁未释放前阻塞
 */
public class Calculator {
    private int calculatedValue;
    private int value;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void calculate(int value) {
        lock.writeLock().lock();
        try {
            this.value = value;
            this.calculatedValue = value*value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getCalculatedValue() {
        lock.readLock().lock();
        try {
            return calculatedValue;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getValue() {
        lock.readLock().lock();
        try {
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }
}
