package com.nop.concurrent.lock;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Semaphore;

/**
 * Created by yangzijun on 17-1-23.
 * when use Semaphore,remember release what you acquire after used.
 * Notice that release doesn't have to be called by the same thread as acquire.
 */
public class ConnectionLimiter {
    private final Semaphore semaphore;

    private ConnectionLimiter(int maxConcurrentRequests) {
        semaphore = new Semaphore(maxConcurrentRequests);
    }

    public URLConnection acquire(URL url) throws InterruptedException,
            IOException {
        semaphore.acquire();
        System.out.println("获取到一个链接");
        return url.openConnection();
    }

    public void release(URLConnection conn) {
        try {
           /*
           * ... clean up here
           */
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ConnectionLimiter limiter = new ConnectionLimiter(5);
        String url = "https://www.baidu.com";
        for (int i = 0; i < 10; i++) {
            limiter.acquire(new URL(url));
        }
    }

    private static class DoubleResourceGrabber implements Runnable {
        private Semaphore first;
        private Semaphore second;

        public DoubleResourceGrabber(Semaphore s1, Semaphore s2) {
            first = s1;
            second = s2;
        }

        public void run() {
            try {
                Thread t = Thread.currentThread();
                first.acquire();
                System.out.println(t + " acquired " + first);

                Thread.sleep(200); // demonstrate deadlock
                second.acquire();
                System.out.println(t + " acquired " + second);

                second.release();
                System.out.println(t + " released " + second);
                first.release();
                System.out.println(t + " released " + first);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }
}

