# Apache Storm Hello World

In order to install Apache Storm in our local machine for tests purposes we can consider using **(1) Apache Storm Binaries** or through **(2) Docker Containers**.

### Pre-requisites

**Setting environment variables**
```bash
echo 'export M2_HOME=/opt/apache-maven-3.6.3' >> ~/.profile
echo 'export PATH=${M2_HOME}/bin:${PATH}' >> ~/.profile
echo 'export STORM_VERSION=2.2.0' >> ~/.profile
echo 'export STORM_HOME=$PWD' >> ~/.profile
echo 'export PATH=$PATH:$STORM_HOME/bin' >> ~/.profile
source ~/.profile
```

If you don't have **Apache Maven** installed, follow the commands below:

```bash
wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip
tar -xf apache-maven-3.6.3-bin.tar.gz
sudo mv apache-maven-3.6.3 /opt
```

### Installing Apache Storm

Below we have two ways to install Apache Storm, the first one is using **Apache Storm Binaries** and the second one is using **Docker Compose**.

#### 1) Installing Apache Storm locally

**Download Apache Storm**
```bash
wget https://www.apache.org/dyn/closer.lua/storm/apache-storm-$STORM_VERSION/apache-storm-$STORM_VERSION.tar.gz
tar -xf apache-storm-$STORM_VERSION.tar.gz
cd apache-storm-$STORM_VERSION
storm version
```

#### 2) Installing Apache Storm using Docker Compose

```bash
docker compose up -d
```

### Apache Storm Examples

Apache Storm comes with a few examples that can be used to test the installation. The examples are located in the `examples` directory.

```bash
# access the examples directory
cd /c/opt/apache-storm-2.6.0/examples/storm-starter

# compile the project
mvn package

# copy the jar file to the project directory
cp /c/opt/apache-storm-2.6.0/examples/storm-starter/target/storm-starter-2.6.0.jar \
  "/<dir>/storm-getting-started"

# open the supervisor container
docker exec -it supervisor bash

# access the project directory
cd /demo

# execute the topology
storm jar storm-starter-2.6.0.jar org.apache.storm.starter.WordCountTopology WordCountTopology
storm jar storm-starter-2.6.0.jar org.apache.storm.starter.WordCountTopologyNode WordCountTopologyNode
storm jar storm-starter-2.6.0.jar org.apache.storm.starter.SlidingWindowTopology SlidingWindowTopology
storm jar storm-starter-2.6.0.jar org.apache.storm.starter.FastWordCountTopology FastWordCountTopology
storm jar storm-starter-2.6.0.jar org.apache.storm.starter.AnchoredWordCount AnchoredWordCount
```
