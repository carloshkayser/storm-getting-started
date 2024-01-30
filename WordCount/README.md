# Word Count Example

Compile
```bash
mvn clean compile assembly:single
```

Open supervisor container
```bash
docker exec -it supervisor bash
```

Access the project directory
```bash
cd /demo/WordCount

# Execute the topology
storm jar \
  target/WordCount-1.0-SNAPSHOT-jar-with-dependencies.jar \
  io.github.carloshkayser.wordcount.topologies.Topology remote

# Execute the topology locally
storm local \
  target/WordCount-1.0-SNAPSHOT-jar-with-dependencies.jar \
  io.github.carloshkayser.wordcount.topologies.Topology local
```
