package io.github.carloshkayser.twitterwordcount.bolts;

import io.github.carloshkayser.twitterwordcount.utils.HashMapToFile;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.io.IOException;
import java.util.*;

public class TweetReportBolt extends BaseRichBolt {

	private HashMap<String, Long> counts = null;
	private HashMapToFile toFile = null;

	@Override
	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.counts = new HashMap<String, Long>();
		this.toFile = new HashMapToFile();
	}
	
	@Override
	public void execute(Tuple tuple) {
		String word = tuple.getStringByField("hashtag");
		Long count = tuple.getLongByField("count");
		this.counts.put(word, count);

		System.out.println("Result: " + word + " : " + count);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// there is nothing to output
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

		try {
			this.toFile.writeResults(this.counts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}