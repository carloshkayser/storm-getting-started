package io.github.carloshkayser.wordcount.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import io.github.carloshkayser.wordcount.spouts.*;
import io.github.carloshkayser.wordcount.bolts.*;

import java.util.Arrays;

public class Topology {

  public static void main(String[] args) throws Exception {

    TopologyBuilder builder = new TopologyBuilder();

    builder.setSpout("spout", new RandomSentenceSpout());
    builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
    builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));
    // builder.setBolt("file", new FileOutputBolt()).globalGrouping("count");
    // builder.setBolt("websocket", new WebSocketBolt()).globalGrouping("count");

    Config conf = new Config();

    conf.setDebug(true);

    // Metrics
    conf.registerMetricsConsumer(org.apache.storm.metric.LoggingMetricsConsumer.class, 1);

    conf.setNumWorkers(4);
    conf.setMaxTaskParallelism(3);

    if (new String(args[0]).equals("remote")) {
      StormSubmitter.submitTopology("wordCountTopology", conf, builder.createTopology());

    } else {
      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("wordCountTopology", conf, builder.createTopology());
      Thread.sleep(10000);
      cluster.shutdown();
    }
  }
}
