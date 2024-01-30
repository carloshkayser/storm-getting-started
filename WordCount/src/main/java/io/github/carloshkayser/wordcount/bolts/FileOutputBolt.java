package io.github.carloshkayser.wordcount.bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileOutputBolt extends BaseRichBolt {

  private static final long serialVersionUID = 1L;
  private static final String FILENAME = "wordcount.txt";
  private BufferedWriter writer;

  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    try {
      writer = new BufferedWriter(new FileWriter(FILENAME));
    } catch (IOException e) {
      throw new RuntimeException("Error opening file " + FILENAME, e);
    }
  }

  @Override
  public void execute(Tuple input) {
    String word = input.getStringByField("word");
    Integer count = input.getIntegerByField("count");
    try {
      writer.write(word + ": " + count);
      writer.newLine();
      writer.flush();
    } catch (IOException e) {
      throw new RuntimeException("Error writing to file " + FILENAME, e);
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    // No output
  }

  @Override
  public void cleanup() {
    try {
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException("Error closing file " + FILENAME, e);
    }
  }
}
