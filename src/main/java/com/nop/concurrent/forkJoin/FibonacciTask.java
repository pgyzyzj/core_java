package com.nop.concurrent.forkJoin;

import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by yangzijun on 17-1-22.
 * wtf:在我本地测试的时候总是单线程的更快
 */
public class FibonacciTask extends RecursiveTask<Long> {

    private static final long serialVersionUID = 6136927121059165206L;

    private static final int THRESHOLD = 5;
    Map<Integer, Long> cache = new HashMap<>();

    private FibonacciProblem problem;
    public long result;

    public FibonacciTask(FibonacciProblem problem) {
        this.problem = problem;
    }

    @Override
    public Long compute() {
        if (problem.n < THRESHOLD) { // easy problem, don't bother with parallelism
            result = problem.solve();
        } else {
            FibonacciTask worker1 = new FibonacciTask(new FibonacciProblem(problem.n - 1));
            FibonacciTask worker2 = new FibonacciTask(new FibonacciProblem(problem.n - 2));
            worker1.fork();
            result = worker2.compute() + worker1.join();
        }
        return result;
    }

    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("No of processors: " + processors);
        int n = 43;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        FibonacciProblem bigProblem = new FibonacciProblem(n);
        FibonacciTask task = new FibonacciTask(bigProblem);
        ForkJoinPool pool = new ForkJoinPool(processors);
        pool.invoke(task);
        long result = task.result;
        stopWatch.stop();
        System.out.println("Computed Result: " + result);
        System.out.println("Elapsed Time: " + stopWatch.getTime());

    }
}

