package com.nop.concurrent.forkJoin;

import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzijun on 17-1-22.
 * This is a scenario where the fork/join framework can improve the performance.
 * Notice that if you want to use this feature before jdk1.7+,jsr166.jar should be added to jvm boot classpath.
 * In the following FibonacciTask we will use 2 worker threads to do the same work but more efficiently
 */
public class FibonacciProblem {

    public int n;
    Map<Integer, Long> cache = new HashMap<>();

    public FibonacciProblem(int n) {
        this.n = n;
    }

    public long solve() {
        return fibonacci(n);
    }

    private long fibonacci(int n) {
        if (n <= 1)
            return n;
        else {
            long result = fibonacci(n - 1) + fibonacci(n - 2);
            if (cache.containsKey(Integer.valueOf(n))) {
                return cache.get(Integer.valueOf(n));
            }
            cache.put(Integer.valueOf(n), result);
            return result;
        }
    }

    public static void main(String[] args) {
        int n = 43;
        //1 1 2 3 5 8 13 21 34 55
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        FibonacciProblem bigProblem = new FibonacciProblem(n);
        long result = bigProblem.solve();
        stopWatch.stop();
        System.out.println("Computing Fib number: " + n);
        System.out.println("Computed Result: " + result);
        System.out.println("Elapsed Time: " + stopWatch.getTime());

    }

}

