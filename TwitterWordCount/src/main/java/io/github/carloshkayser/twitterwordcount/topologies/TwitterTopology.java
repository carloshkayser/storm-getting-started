package io.github.carloshkayser.twitterwordcount.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
 
import io.github.carloshkayser.twitterwordcount.spouts.TwitterStreamSpout;
import io.github.carloshkayser.twitterwordcount.bolts.TweetSplitBolt;
import io.github.carloshkayser.twitterwordcount.bolts.TweetWordCountBolt;
import io.github.carloshkayser.twitterwordcount.bolts.TweetReportBolt;
import org.apache.storm.tuple.Fields;

import java.util.Arrays;

public class TwitterTopology {
    public static void main(String args[]) {

        try {
            // Used to filter the tweets
            String[] arguments = args.clone();
            String[] keyWords = Arrays.copyOfRange(arguments, 0, arguments.length);

            for (int i=0; i<keyWords.length; i++) {
                System.out.printf("keyWords %s \n", keyWords[i]);
            }
        
            Config conf = new Config();
            conf.setDebug(true);

            // Building a topology
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("twitterStreamSpout", new TwitterStreamSpout(keyWords));
            builder.setBolt("tweetSplitBolt", new TweetSplitBolt(), 2).shuffleGrouping("twitterStreamSpout");
            builder.setBolt("tweetWordCountBolt", new TweetWordCountBolt()).shuffleGrouping("tweetSplitBolt");
            builder.setBolt("tweetReportBolt", new TweetReportBolt()).shuffleGrouping("tweetWordCountBolt");

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("twitterTopology", conf, builder.createTopology());
            
            Thread.sleep(60000);
            cluster.shutdown();

        } catch (Exception e) {
            System.out.println("Something went wrong.");
            System.out.println(e.toString());
        }

        System.exit(0);
    }
}