# Cassandra Activity Log App

A Spring Boot REST API for tracking user activity logs, backed by a 3-node Apache Cassandra cluster. Activities are automatically deleted after 30 days via Cassandra TTL.

## How to run the project

```bash
./gradlew build

./gradlew bootRun
```

If you want to debug, use the next line instead and attach a debugger using a Remote JVM Debug configuration:

```bash
./gradlew bootRun --debug-jvm
```

## Requirements

```
Java 17
Gradle 8+
Docker
```

---

## Apache Cassandra – 3-node cluster

### Start the cluster

```bash
docker-compose up -d
```

> Note: This takes about 5-10 minutes. Each node waits for the previous one to pass its healthcheck before starting.

### Load the CQL script (schema + seed data)

```bash
docker exec -it cassandra-1 cqlsh -f /scripts/data.cql
```

### Connect to cqlsh

```bash
docker exec -it cassandra-1 cqlsh
docker exec -it cassandra-2 cqlsh
docker exec -it cassandra-3 cqlsh
```

### Clean up

```bash
docker-compose down -v
```

---

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| `POST` | `/activities` | Create a new activity |
| `GET` | `/activities/user/{userId}` | Get all activities for a user |
| `GET` | `/activities/user/{userId}/recent?limit=10` | Get last N activities for a user |
| `GET` | `/activities/user/{userId}/range?from=...&to=...` | Get activities within a time range |

Timestamps must be in ISO 8601 format, e.g. `2026-05-04T10:00:00Z`.