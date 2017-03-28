package com.nop.zk.ClientFramework;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by yangzijun on 17-3-28.
 */
public class CuratorClient {
    public static void main(String[] args) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();
        try {
            System.out.println(new String(client.getData().forPath("/")));
            //client.getData().in
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            client.close();
        }

    }
}
