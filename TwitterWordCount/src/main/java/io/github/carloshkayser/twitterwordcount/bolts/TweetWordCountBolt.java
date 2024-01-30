package io.github.carloshkayser.twitterwordcount.bolts;

import io.github.carloshkayser.twitterwordcount.utils.HashMapToFile;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.io.IOException;
import java.util.*;

public class TweetWordCountBolt extends BaseRichBolt {

	private OutputCollector collector;
	private HashMap<String, Long> counts = null;
	private HashMapToFile toFile = null;

	@Override
	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.counts = new HashMap<String, Long>();
		this.toFile = new HashMapToFile();
	}

	@Override
	public void execute(Tuple tuple) {
		try {
			String word = tuple.getStringByField("hashtag");
			Long count = this.counts.get(word);
			if (count == null) {
				count = 0L;
			}
			count++;
			this.counts.put(word, count);
			this.writeToFile(this.counts);
			this.collector.ack(tuple);

		} catch (Exception e) {
			this.collector.reportError(e);
			this.collector.fail(tuple);
		}
	}

	private void writeToFile(HashMap<String, Long> counts) {
		try {
			this.toFile.clearOutput();
			this.toFile.writeResults(counts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cleanup() {
		System.out.println("--- FINAL COUNTS ---");
		List<String> keys = new ArrayList<String>();
		keys.addAll(this.counts.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			System.out.println(key + " : " + this.counts.get(key));
		}
		System.out.println("--------------------");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// declarer.declare(new Fields("hashtag", "count"));
		// there is nothing to output
	}

}