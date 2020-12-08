# Apache Storm Hello World

In order to install Apache Storm in our local machine for tests purpose we can consider using **(1) Apache Storm Binaries** or through **(2) Docker Containers**.



#### 1) Installing Apache Storm locally

**Setting environment variables**

```bash
echo 'export M2_HOME=/opt/apache-maven-3.6.3' >> ~/.profile
echo 'export PATH=${M2_HOME}/bin:${PATH}' >> ~/.profile
echo 'export STORM_VERSION=2.2.0' >> ~/.profile
echo 'export STORM_HOME=$PWD' >> ~/.profile
echo 'export PATH=$PATH:$STORM_HOME/bin' >> ~/.profile
source ~/.profile
```

**Download Apache Storm**
```bash
wget https://www.apache.org/dyn/closer.lua/storm/apache-storm-$STORM_VERSION/apache-storm-$STORM_VERSION.tar.gz
tar -xf apache-storm-$STORM_VERSION.tar.gz
cd apache-storm-$STORM_VERSION
storm version
```



#### 2) Installing Apache Storm using Docker Compose

```bash
docker-compose up -d
```



### Twitter HashTag Count

This topology written in Java using **Apache Maven**, consists of **one [spout](http://storm.apache.org/releases/current/Concepts.html#spouts)** and **two [bolts](http://storm.apache.org/releases/current/Concepts.html#bolts)**. Basically, the spout tracks the Tweets and sent it to **Tweet split bolt** which gets the hashtags from Tweets and sent it to the **hashtag count bolt**. Finally, the results are placed in to a text file.



If you don't have **Apache Maven** installed, follow the commands below:

```bash
wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip
tar -xf apache-maven-3.6.3-bin.tar.gz
sudo mv apache-maven-3.6.3 /opt
```



To **build and test** the topology locally, run the command below (you need to install Apache Storm Binaries to use this) :

```bash
mvn compile exec:java -Dstorm.topology=io.github.carloshkayser.twitterwordcount.topologies.TwitterTopology -Dexec.args="keyWord1 keyWord2"
```



To **build** the topology and generate a **jar** file with our topology, consider the command below:

```bash
mvn clean compile assembly:single
```



To run the topology in a **local Apache Storm cluster** use the command below:

```bash
storm local target/TwitterWordCount-1.0-SNAPSHOT-jar-with-dependencies.jar io.github.carloshkayser.twitterwordcount.topologies.TwitterTopology local keyWord1 keyWord2
```



To run the topology in an **Apache Storm cluster** use the command below:

```bash
storm jar target/TwitterWordCount-1.0-SNAPSHOT-jar-with-dependencies.jar io.github.carloshkayser.twitterwordcount.topologies.TwitterTopology remote keyWord1 keyWord2
```



**Resources**

- https://docs.microsoft.com/pt-br/azure/hdinsight/storm/apache-storm-develop-java-topology