package com.nop.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created by yangzijun on 17-3-13.
 */
public class ExclamationBolt implements IRichBolt {

    OutputCollector collector;
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector=collector;
    }

    public void execute(Tuple input) {
        collector.emit(input,new Values(input.getString(0)+"!!!"));
        collector.ack(input);
    }

    public void cleanup() {
        System.out.println("ExclamationBolt's cleanUp.");
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));

    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
