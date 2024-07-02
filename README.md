# Message Queue
2024-05-14 ~


# Cluster command for dev environment

```bash
./gradlew bootRun --args='--server.port=8080 --node.id=1' # leader
./gradlew bootRun --args='--server.port=8081 --node.id=2' # follower
./gradlew bootRun --args='--server.port=8082 --node.id=3' # follower
```
