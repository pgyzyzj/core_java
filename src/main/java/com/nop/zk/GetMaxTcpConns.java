package com.nop.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by yangzijun on 17-3-27.
 * 当前系统上启动zk服务，ulimit －n  1024 ，此例用于演示获取单进程支持的tcp最大连接数
 * 下例for语句中，i设置为７０就会报错，这个是不是zk服务端配置限制了，看看,哈哈，果然，zk服务端默认有maxClientCnxns参数限制了最大连接数
 */
public class GetMaxTcpConns {

    public static void main(String[] args) {
        for (int i = 0; i< 2048; i++) {
            final int s=i;
            try {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 50000, new Watcher() {
                                @Override
                                public void process(WatchedEvent event) {

                                }
                            });
                            synchronized (this) {
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new IllegalStateException();
                        }
                    }
                });
                t.start();
            }catch(Exception e){
                if (e instanceof IllegalStateException){
                    System.out.println(i);
                    break;
                }
            }
        }
    }
}
