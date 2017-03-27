package com.nop.zk.emulator;

import com.google.common.base.Joiner;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangzijun on 17-3-22.
 */
public class ZkBarrier extends SyncPrimitive {


    private String path;
    private Integer size;
    private String ephemeralNodeName;
    private ZooKeeper zk;
    private AtomicInteger allDone = new AtomicInteger(0);

    public ZkBarrier(String address, String path, Integer size) {
        super(address);
        this.size = size;
        this.path = path;
        this.zk = getZk();
        if (zk != null) {
            try {
                Stat data = zk.exists(path, false);
                if (data == null) {
                    zk.create(path, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean enterBarrier() throws KeeperException, InterruptedException {
        //1.create ephemeral node;
        Thread.sleep(new Random().nextInt(1000));
        zk.create(path + "/" + Thread.currentThread().getName(), new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(Thread.currentThread().getName() + " created node 'path/" + Thread.currentThread().getName() + "'");
        while (true) {
            synchronized (getMutex()) {
                List<String> children = zk.getChildren(path, true);
                Joiner joiner = Joiner.on(",");
                System.out.println(Thread.currentThread().getName() + " " + joiner.join(children));
                if (children.size() < size) {
                    getMutex().wait();
                } else {
                    System.out.println(Thread.currentThread().getName() + " entered barrier..");
                    //getMutex().notifyAll();
                    return true;
                }
            }
        }
    }

    public boolean leave() throws KeeperException, InterruptedException {
        zk.delete(path + "/" + Thread.currentThread().getName(), 0);
        while (true) {
            synchronized (getMutex()) {
                List<String> children = zk.getChildren(path, true);
                Joiner joiner = Joiner.on(",");
                System.out.println(Thread.currentThread().getName() + "before leaving children size: " + joiner.join(children));
                if (children.size() == 0) {
                    int i = allDone.incrementAndGet();
                    System.out.println(Thread.currentThread().getName() + " Leaving barrier.." + i);
                    synchronized (this) {
                        if (i == size) {
                            System.out.println(Thread.currentThread().getName() + " All done,notify main to stop..");
                            this.notify();
                        }
                    }
                    //getMutex().notifyAll();
                    return true;
                } else {
                    getMutex().wait();
                }
            }
        }
    }


    public boolean isAllDone() {
        return this.size == allDone.intValue();
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getEphemeralNodeName() {
        return ephemeralNodeName;
    }

    public void setEphemeralNodeName(String ephemeralNodeName) {
        this.ephemeralNodeName = ephemeralNodeName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
