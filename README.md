# Apache Storm Hello World

This project demonstrates how to use Apache Storm for stream processing. You can run it using either VS Code Dev Containers or manually with Docker Compose.

## Starting the Storm Cluster

1. Install prerequisites:
   - [Docker Desktop](https://www.docker.com/products/docker-desktop/)

2. Start the Storm cluster:
```bash
docker compose up -d --build
```

3. Verify the services are running:
   - Storm UI: http://localhost:8000
   - Nimbus: localhost:6627
   - Zookeeper: localhost:2181


4. Enter the development container:
```bash
docker compose exec dev bash
```

5. Now, go to the topology directory that you want to run.

- [WordCount](WordCount/README.md)
- [TwitterWordCount](TwitterWordCount/README.md)

6. Stopping the Storm Cluster
```bash
docker compose down
```
