package io.github.carloshkayser.wordcount.bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Values;

import org.apache.storm.topology.BasicOutputCollector;

import java.util.Map;

public class SplitSentence extends BaseBasicBolt {
  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("word"));
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }

  public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
    String sentence = tuple.getStringByField("word");
    String words[] = sentence.split(" ");
    for (String w : words) {
      basicOutputCollector.emit(new Values(w));
    }
  }
}