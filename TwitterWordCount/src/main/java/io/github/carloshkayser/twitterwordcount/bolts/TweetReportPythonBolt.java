package io.github.carloshkayser.twitterwordcount.bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;

import java.util.Map;

public class TweetReportPythonBolt extends ShellBolt implements IRichBolt {

    private OutputCollector collector;

    public TweetReportPythonBolt() {
        super("python3", "TweetUpdateDashboard.py");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // there is no output
    }

    // The getComponentConfiguration method allows you to configure various aspects of how this component runs
    @Override
    public Map<String, Object> getComponentConfiguration() { return null; }
}