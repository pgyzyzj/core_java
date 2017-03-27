package com.nop.zk.emulator;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by yangzijun on 17-3-22.
 */
public class SyncPrimitive implements Watcher {
    private String root;
    private ZooKeeper zk;
    private final Object mutex = new Object();

    /**
     *
     * we initialized the ZooKeeper with a watcher object called "this", which will be regarded as the default watcher for
     * every call to zooKeeper's getXXX method with second param to set true
     * @param address
     */

    public SyncPrimitive(String address) {
        if (zk == null) {
            try {
                zk = new ZooKeeper(address, 3000, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        synchronized (mutex){
            System.out.println(Thread.currentThread().getName()+" received a notify...path="+event.getPath()+",type="+event.getType()+",state="+event.getState());
            mutex.notifyAll();
        }
    }


    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public Object getMutex() {
        return mutex;
    }
}
