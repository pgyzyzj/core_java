package com.nop.zk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by yangzijun on 17-3-20.
 */
public class BaseZkApi {

    public static void main(String[] args) {
        try {
            ZooKeeper zk=new ZooKeeper("localhost:2181", 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    Gson gson =new Gson();
                    gson.toJson(watchedEvent,System.out);
                }
            });
            Stat stat=new Stat();
            String s=new String(zk.getData("/yzj",false,stat));
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
