package io.github.carloshkayser.twitterwordcount.spouts;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import twitter4j.*;

@SuppressWarnings("serial")
public class TwitterStreamSpout extends BaseRichSpout {

    // Queue of tweets
    private LinkedBlockingQueue<Status> queue;
    private TwitterStream twitterStream;
    private SpoutOutputCollector collector;
    private String[] keyWords;

    public TwitterStreamSpout(String[] keyWords) {
        this.keyWords = keyWords; // The word used to filter the tweets
    }

    public void open(Map map, TopologyContext context, SpoutOutputCollector collector) {
        System.out.printf("TwitterStreamSpout open");

        this.collector = collector;
        this.twitterStream = new TwitterStreamFactory().getInstance();
        this.queue = new LinkedBlockingQueue<>();

        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                // System.out.println("Next Tweet");
                // System.out.printf("Spout Next Tuple %s", status.toString());
                queue.offer(status);
            }
 
            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {}
 
            @Override
            public void onTrackLimitationNotice(int i) {}
 
            @Override
            public void onScrubGeo(long l, long l1) {}
 
            @Override
            public void onException(Exception e) {}
 
            @Override
            public void onStallWarning(StallWarning warning) {}
        };

        this.twitterStream.addListener(listener);

        if (this.keyWords.length == 0) {
            this.twitterStream.sample();
        }else {
           FilterQuery query = new FilterQuery().track(keyWords);
           // query.language("pt");
           // filterQuery.track("trump");
           this.twitterStream.filter(query);
        }
    }

    @Override
    public void nextTuple() {
        Status ret = queue.poll();
                
        if (ret == null) {
            Utils.sleep(50);
        } else {
            this.collector.emit(new Values(ret));
        }
    }
 
    @Override
    public void deactivate() {
        twitterStream.cleanUp();
    };
 
    @Override
    public void close() {
        twitterStream.shutdown();
    }
 
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tweet"));
    }

}