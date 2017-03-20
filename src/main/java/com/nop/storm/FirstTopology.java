package com.nop.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

/**
 * Created by yangzijun on 17-3-13.
 */
public class FirstTopology {
    public static void main(String[] args) throws InterruptedException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word", new WordSpout(), 2);
        builder.setBolt("exc1", new ExclamationBolt(), 3).shuffleGrouping("word");
        builder.setBolt("exc2", new ExclamationBolt(), 2).shuffleGrouping("exc1");


        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(2);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());
        Utils.sleep(10000);
        cluster.killTopology("test");
        cluster.shutdown();
    }
}
