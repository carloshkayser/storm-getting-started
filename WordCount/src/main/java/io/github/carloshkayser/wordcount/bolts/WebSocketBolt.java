package io.github.carloshkayser.wordcount.bolts;

import io.github.carloshkayser.wordcount.utils.MyWebSocketClient;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class WebSocketBolt extends BaseRichBolt {

  private OutputCollector collector;
  private MyWebSocketClient client;

  @Override
  public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
    this.collector = collector;

    try {
      URI serverUri = new URI("ws://localhost:8765");
      client = new MyWebSocketClient(serverUri);
      client.connect();

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void execute(Tuple tuple) {
    // Process tuple and extract data
    String data = tuple.getStringByField("data");

    // Send data over WebSocket
    client.send(data);

    // Acknowledge the tuple
    collector.ack(tuple);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    // No output fields
  }

  @Override
  public void cleanup() {
    // Close WebSocket connection
    client.close();
  }

}
