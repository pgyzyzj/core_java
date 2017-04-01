package com.nop.zk.ClientFramework;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * Created by yangzijun on 17-3-28.
 * 模拟启动了１０个客户端连接zk集群，在每个上执行echo cons |nc 127.0.0.1 218x ,就可以看到每台服务器上accept到的连接。。从结果可以看出已经是做了负载的，单台机器的负载是比较均匀的，分布为4,4,6
 */
public class CuratorClient {
    public static void main(String[] args) throws InterruptedException {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        List<String> servers = Lists.newArrayList("127.0.0.1:2181", "127.0.0.1:2182", "127.0.0.1:2183");
        List<CuratorFramework> clients = Lists.newArrayList();
        for (int i = 0; i < 10; ++i) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(Joiner.on(",").join(servers).toString(), retryPolicy);
            client.start();
            clients.add(client);
        }

        Thread.sleep(10000);
        for (int i = 0; i < 10; ++i) {
            clients.get(i).close();
        }
    }
}
