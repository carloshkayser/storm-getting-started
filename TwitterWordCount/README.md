# Twitter HashTag Count

This topology written in Java using **Apache Maven**, consists of **one [spout](http://storm.apache.org/releases/current/Concepts.html#spouts)** and **two [bolts](http://storm.apache.org/releases/current/Concepts.html#bolts)**. Basically, the spout tracks the Tweets and sent it to **Tweet split bolt** which gets the hashtags from Tweets and sent it to the **hashtag count bolt**. Finally, the results are placed in to a text file.

To **build and test** the topology locally, run the command below (you need to install Apache Storm Binaries to use this):
```bash
cd TwitterWordCount

# mvn compile exec:java -Dstorm.topology=io.github.carloshkayser.twitterwordcount.topologies.TwitterTopology -Dexec.args="keyWord1 keyWord2"
```

Configure the **Twitter API** in the `src/main/resources/twitter4j.properties` file.
```properties
oauth.consumerKey=
oauth.consumerSecret=
oauth.accessToken=
oauth.accessTokenSecret=
```

To **build** the topology and generate a **jar** file with our topology, consider the command below:
```bash
mvn clean compile assembly:single
```

To run the topology in a **local Apache Storm cluster** use the command below:
```bash
storm \
  local target/TwitterWordCount-1.0-SNAPSHOT-jar-with-dependencies.jar \
  io.github.carloshkayser.twitterwordcount.topologies.TwitterTopology \
  local keyWord1 keyWord2
```

To run the topology in an **Apache Storm cluster** use the command below:
```bash
storm \
  jar target/TwitterWordCount-1.0-SNAPSHOT-jar-with-dependencies.jar \
  io.github.carloshkayser.twitterwordcount.topologies.TwitterTopology \
  remote keyWord1 keyWord2
```

**Resources**

- https://docs.microsoft.com/pt-br/azure/hdinsight/storm/apache-storm-develop-java-topology
