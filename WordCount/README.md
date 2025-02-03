# Word Count Example

> Inside the dev container

Access the project directory
```bash
cd /demo/WordCount
```

Compile
```bash
mvn clean compile assembly:single
```

Run the WordCount topology in a remote cluster
```bash
storm jar \
  target/WordCount-1.0-SNAPSHOT-jar-with-dependencies.jar \
  io.github.carloshkayser.wordcount.topologies.Topology remote
```

Check the topology status
```bash
storm list
```

Monitor the topology
```bash
# TODO
```

Kill the topology
```bash
storm kill wordCountTopology
```

Execute the topology locally
```bash
storm local \
  target/WordCount-1.0-SNAPSHOT-jar-with-dependencies.jar \
  io.github.carloshkayser.wordcount.topologies.Topology local
```

Check the results in the `wordcount.txt` file.
```bash
cat wordcount.txt
```
