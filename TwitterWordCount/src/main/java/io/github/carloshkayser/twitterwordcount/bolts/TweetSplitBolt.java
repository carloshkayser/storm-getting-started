package io.github.carloshkayser.twitterwordcount.bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import twitter4j.HashtagEntity;
import twitter4j.Status;

import java.util.Map;

public class TweetSplitBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple tuple) {
		try {
		
			Status tweet = (Status) tuple.getValueByField("tweet");

			// Get HashTag only
			for(HashtagEntity hashtag : tweet.getHashtagEntities()) {
				this.collector.emit(new Values(hashtag.getText()));
			}

			// Split
			// String[] words = tweet.getText().split(" ");
			// for(String word : words){
			// 	this.collector.emit(new Values(word));
			// }
			
			// Mecanismo utilizado para rastrear tuplas que falharam
			// Reprocessando-a novamente caso for o caso
			this.collector.ack(tuple);

		} catch (Exception e) {
			this.collector.reportError(e);
			this.collector.fail(tuple);
		}
		
	}

	// The declareOutputFields method declares that the bolt emits 1-tuples with one field called "hashtag".
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("hashtag"));
	}
}