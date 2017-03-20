package com.nop.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * Created by yangzijun on 17-3-13.
 */
public class WordSpout implements IRichSpout {
    private SpoutOutputCollector collector;

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void close() {
        System.out.println("WordSpout close method invoked.");
    }

    public void activate() {
        System.out.println("WordSpout activate");

    }

    public void deactivate() {
        System.out.println("WordSpout deactivate");
    }

    public void nextTuple() {
        Utils.sleep(3000);
        final String[] words = new String[]{"a", "e", "i", "o", "u"};
        final Random rand = new Random();
        final String word = words[rand.nextInt(words.length)];
        collector.emit(new Values(word));
    }

    public void ack(Object msgId) {
        System.out.println("WordSpout ack:" + msgId);
    }

    public void fail(Object msgId) {
        System.out.println("WordSpout fail:" + msgId);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("world"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
