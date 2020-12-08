package io.github.carloshkayser.twitterwordcount.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
 
import io.github.carloshkayser.twitterwordcount.spouts.TwitterStreamSpout;
import io.github.carloshkayser.twitterwordcount.bolts.TweetSplitBolt;
import io.github.carloshkayser.twitterwordcount.bolts.TweetWordCountBolt;

import java.util.Arrays;

public class TwitterTopology {
    public static void main(String args[]) {

        try {
            // Used to filter the tweets
            String[] arguments = args.clone();
            String[] keyWords = Arrays.copyOfRange(arguments, 1, arguments.length);

            for (int i=0; i<keyWords.length; i++) {
                System.out.printf("keyWords %s \n", keyWords[i]);
            }
        
            Config conf = new Config();
            conf.setDebug(false);

            // Building a topology
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("twitterStreamSpout", new TwitterStreamSpout(keyWords));
            builder.setBolt("tweetSplitBolt", new TweetSplitBolt(), 2).shuffleGrouping("twitterStreamSpout");
            builder.setBolt("tweetWordCountBolt", new TweetWordCountBolt()).shuffleGrouping("tweetSplitBolt");

            if (new String(args[0]).equals("remote")) {
                //parallelism hint to set the number of workers
                conf.setNumWorkers(4);
                //submit the topology
                StormSubmitter.submitTopology("twitterTopology", conf, builder.createTopology());

            } else { //Otherwise, we are running locally
                //Cap the maximum number of executors that can be spawned
                //for a component to 3
                conf.setMaxTaskParallelism(3);
                //LocalCluster is used to run locally
                LocalCluster cluster = new LocalCluster();
                //submit the topology
                cluster.submitTopology("twitterTopology", conf, builder.createTopology());
                //sleep
                Thread.sleep(60000);
                //shut down the cluster
                cluster.shutdown();

            }

        } catch (Exception e) {
            System.out.println("Something went wrong.");
            System.out.println(e.toString());
        }

        // System.exit(0);
    }
}